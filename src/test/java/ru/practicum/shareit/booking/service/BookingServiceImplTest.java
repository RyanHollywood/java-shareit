package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @BeforeEach
    void reload() {
        bookingRequestDto = new BookingRequestDto(1, 1, LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusHours(1));
        item = new Item(1, "Item", "ItemDescription", true, 1, 1);
        user = new User(2, "User", "Email@email.com");
        booking = new Booking(LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusHours(1), item, user, BookingStatus.WAITING);
        booking.setId(1);
    }

    @Test
    void create() throws Exception {
        checkItem();
        checkUser();
        when(bookingRepository.save(any()))
                .thenReturn(booking);
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.create(bookingRequestDto));

    }

    @Test
    void update() {
    }

    @Test
    void getById() {
        checkItem();
        checkBooking();
        assertEquals(BookingMapper.toBookingDto(booking), bookingService.getById(1, 1));
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllByOwner() {
    }

    @Test
    void delete() {
    }

    private void checkItem() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
    }

    private void checkUser() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
    }

    private void checkBooking() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.of(booking));
    }
}