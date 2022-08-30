package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.errors.BadRequest;
import ru.practicum.shareit.exceptions.errors.InternalServerError;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    //private final BookingStatus DEFAULT_STATUS = BookingStatus.WAITING;
    private final BookingStatus defaultStatus = BookingStatus.WAITING;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingDto create(BookingRequestDto requestDto) {
        Item itemToBook = getItem(requestDto.getItemId());
        if (!itemToBook.isAvailable()) {
            log.warn("Item not available");
            throw new BadRequest("Item not available");
        }
        User booker = getUser(requestDto.getBookerId());
        if (itemToBook.getOwnerId() == booker.getId()) {
            log.warn("Booker and owner is same person");
            throw new NotFound("Booker and owner is same person");
        }
        if (requestDto.getStart().isBefore(LocalDateTime.now()) || requestDto.getEnd().isBefore(LocalDateTime.now())) {
            log.warn("Start time and end time cannot be in past");
            throw new BadRequest("Start time and end time cannot be in past");
        }
        Booking booking = BookingMapper.toBooking(requestDto);
        booking.setItem(itemToBook);
        booking.setBooker(booker);
        booking.setStatus(defaultStatus);
        log.debug("Booking created");
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto update(long userId, long id, boolean parameter) {
        Booking bookingToUpdate = getBooking(id);
        Item item = getItem(bookingToUpdate.getItem().getId());
        if (userId != item.getOwnerId()) {
            log.warn("User is not owner");
            throw new NotFound("User is not owner");
        }
        if (parameter) {
            if (bookingToUpdate.getStatus() == BookingStatus.APPROVED) {
                throw new BadRequest("Booking already approved");
            }
            bookingToUpdate.setStatus(BookingStatus.APPROVED);
        } else {
            if (bookingToUpdate.getStatus() == BookingStatus.REJECTED) {
                throw new BadRequest("Booking already rejected");
            }
            bookingToUpdate.setStatus(BookingStatus.REJECTED);
        }
        log.debug("Booking updated");
        return BookingMapper.toBookingDto(bookingRepository.save(bookingToUpdate));
    }

    @Override
    public BookingDto getById(long userId, long id) {
        Booking booking = getBooking(id);
        Item itemToBook = getItem(booking.getItem().getId());
        if (userId != itemToBook.getOwnerId()) {
            if (userId != booking.getBooker().getId()) {
                log.warn("Booker is not owner or booker");
                throw new NotFound("Booker is not owner or booker");
            }
        }
        log.debug("Booking found");
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAll(long userId, String state, Optional<Integer> from, Optional<Integer> size) {
        if (from.isPresent() && size.isPresent()) {
            if (from.get() < 0 || size.get() <= 0) {
                throw new BadRequest("From and size parameters are negative or equal zero");
            }
            return filterByState(state, bookingRepository.findByBookerIdOrderByStartDesc(userId, PageRequest.of(from.get() / size.get(), size.get())));
        }
        getUser(userId);
        log.debug("Bookings by booker found");
        return filterByState(state, bookingRepository.findByBookerIdOrderByStartDesc(userId));
    }

    @Override
    public List<BookingDto> getAllByOwner(long ownerId, String state, Optional<Integer> from, Optional<Integer> size) {
        getUser(ownerId);
        List<Long> itemsIds = itemRepository.findAllByOwnerId(ownerId).stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        if (from.isPresent() && size.isPresent()) {
            if (from.get() < 0 || size.get() <= 0) {
                throw new BadRequest("From and size parameters are negative or equal zero");
            }
            return filterByState(state, bookingRepository.findAllByItemIdInOrderByStartDesc(itemsIds, PageRequest.of(from.get() / size.get(), size.get())));
        }
        log.debug("Bookings by owner found");
        return filterByState(state, bookingRepository.findAllByItemIdInOrderByStartDesc(itemsIds));
    }

    @Override
    public void delete(long id) {
        getBooking(id);
        bookingRepository.deleteById(id);
        log.debug("Booking deleted");
    }

    private Booking getBooking(long id) {
        return bookingRepository.findById(id).orElseThrow(() -> {
            log.warn("Booking not found");
            throw new NotFound("Booking not found");
        });
    }

    private Item getItem(long id) {
        return itemRepository.findById(id).orElseThrow(() -> {
            log.warn("Item not found");
            throw new NotFound("Item not found");
        });
    }

    private User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found");
            throw new NotFound("User not found");
        });
    }

    private List<BookingDto> filterByState(String state, List<Booking> bookings) {
        switch (state) {
            case "ALL":
                return bookings.stream()
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "CURRENT":
                return bookings.stream()
                        .filter(booking -> LocalDateTime.now().isAfter(booking.getStart()) && LocalDateTime.now().isBefore(booking.getEnd()))
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "PAST":
                return bookings.stream()
                        .filter(booking -> LocalDateTime.now().isAfter(booking.getEnd()))
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "FUTURE":
                return bookings.stream()
                        .filter(booking -> LocalDateTime.now().isBefore(booking.getStart()))
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "WAITING":
                return bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.WAITING))
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            case "REJECTED":
                return bookings.stream()
                        .filter(booking -> booking.getStatus().equals(BookingStatus.REJECTED))
                        .map(BookingMapper::toBookingDto)
                        .collect(Collectors.toList());
            default:
                throw new InternalServerError("Unknown state: " + state);
        }
    }
}
