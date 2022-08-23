package ru.practicum.shareit.booking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.errors.BadRequest;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.exceptions.errors.InternalServerError;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
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
        Item itemToBook = itemRepository.findById(requestDto.getItemId()).orElseThrow(() -> {
            log.warn("Item to book not found");
            throw new NotFound("Item to book not found");
        });
        if (!itemToBook.isAvailable()) {
            log.warn("Item not available");
            throw new BadRequest("Item not available");
        }
        User booker = userRepository.findById(requestDto.getBookerId()).orElseThrow(() -> {
            log.warn("Booker not found");
            throw new NotFound("Booker not found");
        });
        if (itemToBook.getOwnerId() == booker.getId()) {
            log.warn("Booker and owner is same person");
            throw new NotFound("Booker and owner is same person");
        }
        if (requestDto.getStart().isBefore(LocalDateTime.now()) || requestDto.getEnd().isBefore(LocalDateTime.now())) {
            log.warn("Start time and end time cant be in past");
            throw new BadRequest("Start time and end time cant be in past");
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
        Booking bookingToUpdate = bookingRepository.findById(id).orElseThrow(() -> {
            log.warn("Booking not found");
            throw new NotFound("Booking not found");
        });
        Item item = itemRepository.findById(bookingToUpdate.getItem().getId()).orElseThrow(() -> {
            log.warn("Item not found");
            throw new NotFound("Item not found");
        });
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
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> {
            log.warn("Booking not found");
            throw new NotFound("Booking not found");
        });
        Item itemToBook = itemRepository.findById(booking.getItem().getId()).orElseThrow(() -> {
            log.warn("Item not found");
            throw new NotFound("Item not found");
        });
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
    public List<BookingDto> getAll(long userId, String state) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User not found");
            throw new NotFound("User not found");
        });
        log.debug("Bookings by booker found");
        return filterByState(state, bookingRepository.findByBookerIdOrderByStartDesc(userId));
    }

    @Override
    public List<BookingDto> getAllByOwner(long ownerId, String state) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> {
            log.warn("User not found");
            throw new NotFound("User not found");
        });
        List<Long> itemsIds = itemRepository.findAllByOwnerId(ownerId).stream()
                .map(Item::getId)
                .collect(Collectors.toList());
        log.debug("Bookings by owner found");
        return filterByState(state, bookingRepository.findAllByItemIdInOrderByStartDesc(itemsIds));
    }

    @Override
    public void delete(long id) {
        bookingRepository.deleteById(id);
        log.debug("Booking deleted");
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
