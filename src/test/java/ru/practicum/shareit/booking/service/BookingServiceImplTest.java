package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private BookingRequestDto bookingRequestDto;
    private Item item;
    private User user;
    private final LocalDateTime start = LocalDateTime.now().plusMinutes(30);
    private final LocalDateTime end = LocalDateTime.now().plusHours(1);

    @BeforeEach
    void setUp() {
        bookingRequestDto = new BookingRequestDto(1, 1, start, end);
        item = new Item(1, "Item", "ItemDescription", true, 1, 1);
        user = new User(2, "User", "Email@email.com");
        booking = new Booking(start, end, item, user, BookingStatus.WAITING);
        booking.setId(1);
    }

    @Test
    void create() {
        checkItemOk();
        checkUserOk();
        when(bookingRepository.save(any()))
                .thenReturn(booking);
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.create(bookingRequestDto));
    }

    @Test
    void createWithItemNotAvailable() {
        item.setAvailable(false);
        checkItemOk();
        try {
            bookingService.create(bookingRequestDto);
        } catch (BadRequest exception) {
            assertEquals("Item not available", exception.getMessage());
        }
    }

    @Test
    void createWithBookerSamePerson() {
        user.setId(1);
        item.setAvailable(true);
        checkUserOk();
        checkItemOk();
        checkItemOk();
        try {
            bookingService.create(bookingRequestDto);
        } catch (NotFound exception) {
            assertEquals("Booker and owner is same person", exception.getMessage());
        }
        user.setId(2);
    }

    @Test
    void createInPast() {
        checkItemOk();
        checkUserOk();
        bookingRequestDto.setStart(start.minusHours(2));
        bookingRequestDto.setEnd(end.minusHours(1));
        try {
            bookingService.create(bookingRequestDto);
        } catch (BadRequest exception) {
            assertEquals("Start time and end time cannot be in past", exception.getMessage());
        }
    }

    @Test
    void update() {
        create();
        checkBookingOk();
        when(bookingRepository.save(any()))
                .thenReturn(booking);
        assertEquals(BookingStatus.APPROVED, bookingService.update(1, 1, true).getStatus());

        create();
        checkBookingOk();
        when(bookingRepository.save(any()))
                .thenReturn(booking);
        assertEquals(BookingStatus.REJECTED, bookingService.update(1, 1, false).getStatus());
    }

    @Test
    void updateAlreadyUpdated() {
        checkBookingOk();
        checkItemOk();
        booking.setStatus(BookingStatus.APPROVED);
        try {
            bookingService.update(1, 1, true);
        } catch (BadRequest exception) {
            assertEquals("Booking already approved", exception.getMessage());
        }

        booking.setStatus(BookingStatus.REJECTED);
        try {
            bookingService.update(1, 1, false);
        } catch (BadRequest exception) {
            assertEquals("Booking already rejected", exception.getMessage());
        }
    }

    @Test
    void updateUserNotOwner() {
        create();
        checkBookingOk();
        try {
            bookingService.update(2, 1, true);
        } catch (NotFound exception) {
            assertEquals("User is not owner", exception.getMessage());
        }
    }

    @Test
    void getByIdByBooker() {
        checkItemOk();
        checkBookingOk();
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getById(1, 1));
    }

    @Test
    void getByIdItemNotFound() {
        checkItemNotFound();
        checkBookingOk();
        try {
            bookingService.getById(1, 1);
        } catch (NotFound exception) {
            assertEquals("Item not found", exception.getMessage());
        }
    }

    @Test
    void getByIdBookingNotFound() {
        checkBookingNotFound();
        try {
            bookingService.getById(1, 1);
        } catch (NotFound exception) {
            assertEquals("Booking not found", exception.getMessage());
        }
    }

    @Test
    void getByIdByNotItemOwner() {
        checkBookingOk();
        checkItemOk();
        try {
            bookingService.getById(3, 1);
        } catch (NotFound exception) {
            assertEquals("Booker is not owner or booker", exception.getMessage());
        }
    }

    @Test
    void getAll() {
        create();
        when(bookingRepository.findByBookerIdOrderByStartDesc(anyLong()))
                .thenReturn(List.of(booking));
        assertEquals(1, bookingService.getAll(1, "ALL",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "ALL",
                Optional.empty(), Optional.empty()).get(0));

        booking.setStatus(BookingStatus.WAITING);
        create();
        assertEquals(1, bookingService.getAll(1, "WAITING",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "WAITING",
                Optional.empty(), Optional.empty()).get(0));

        booking.setStatus(BookingStatus.REJECTED);
        create();
        assertEquals(1, bookingService.getAll(1, "REJECTED",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "REJECTED",
                Optional.empty(), Optional.empty()).get(0));

        booking.setStart(LocalDateTime.now().minusMinutes(30));
        create();
        assertEquals(1, bookingService.getAll(1, "CURRENT",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "CURRENT",
                Optional.empty(), Optional.empty()).get(0));

        create();

        booking.setEnd(booking.getEnd().minusHours(2));
        assertEquals(1, bookingService.getAll(1, "PAST",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "PAST",
                Optional.empty(), Optional.empty()).get(0));

        booking.setStart(LocalDateTime.now().plusMinutes(10));
        create();
        assertEquals(1, bookingService.getAll(1, "FUTURE",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "FUTURE",
                Optional.empty(), Optional.empty()).get(0));

        String unknownState = "SOMESTATE";
        try {
            bookingService.getAll(1, unknownState, Optional.empty(), Optional.empty());
        } catch (InternalServerError exception) {
            assertEquals("Unknown state: " + unknownState, exception.getMessage());
        }
    }

    @Test
    void getAllWithPaging() {
        create();
        when(bookingRepository.findByBookerIdOrderByStartDesc(anyLong(), any()))
                .thenReturn(List.of(booking));
        assertEquals(1, bookingService.getAll(1, "ALL",
                Optional.of(0), Optional.of(1)).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAll(1, "ALL",
                Optional.of(0), Optional.of(1)).get(0));
    }

    @Test
    void getAllWrongFromAndSize() {
        try {
            bookingService.getAll(1, "ALL", Optional.of(-1), Optional.of(-1));
        } catch (BadRequest exception) {
            assertEquals("From and size parameters are negative or equal zero", exception.getMessage());
        }
    }

    @Test
    void getAllByOwnerWithoutPaging() {
        create();
        checkUserOk();
        when(itemRepository.findAllByOwnerId(anyLong()))
                .thenReturn(List.of(item));
        when(bookingRepository.findAllByItemIdInOrderByStartDesc(anyList()))
                .thenReturn(List.of(booking));
        assertEquals(1, bookingService.getAllByOwner(1, "ALL",
                Optional.empty(), Optional.empty()).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAllByOwner(1, "ALL",
                Optional.empty(), Optional.empty()).get(0));
    }

    @Test
    void getAllByOwnerWithPaging() {
        create();
        checkUserOk();
        when(itemRepository.findAllByOwnerId(anyLong()))
                .thenReturn(List.of(item));
        when(bookingRepository.findAllByItemIdInOrderByStartDesc(anyList(), any()))
                .thenReturn(List.of(booking));
        assertEquals(1, bookingService.getAllByOwner(1, "ALL",
                Optional.of(0), Optional.of(1)).size());
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getAllByOwner(1, "ALL",
                Optional.of(0), Optional.of(1)).get(0));
    }

    @Test
    void getAllByOwnerWrongFromAndSize() {
        checkUserOk();
        try {
            bookingService.getAllByOwner(1, "ALL", Optional.of(-1), Optional.of(-1));
        } catch (BadRequest exception) {
            assertEquals("From and size parameters are negative or equal zero", exception.getMessage());
        }
    }

    @Test
    void delete() {
        checkBookingOk();
        bookingService.delete(1);
        Mockito.verify(bookingRepository, times(1))
                .deleteById(anyLong());
    }

    private void checkItemOk() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
    }

    private void checkItemNotFound() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
    }

    private void checkUserOk() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
    }

    private void checkUserNotFound() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
    }

    private void checkBookingOk() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(booking));
    }

    private void checkBookingNotFound() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
    }
}