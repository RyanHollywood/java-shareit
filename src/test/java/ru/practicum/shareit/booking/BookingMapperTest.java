package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    private final long id = 1;
    private final LocalDateTime start = LocalDateTime.now();
    private final LocalDateTime end = LocalDateTime.now().plusHours(1);
    private final Item item = null;
    private final User booker = null;
    private final BookingStatus defaultStatus = BookingStatus.WAITING;
    private Booking booking;
    private BookingDto bookingDto;
    private BookingRequestDto bookingRequestDto;
    private BookingItemDto bookingItemDto;

    @BeforeEach
    void reload() {
        booking = new Booking(start, end, null, null, defaultStatus);
        bookingDto = new BookingDto(id, start, end, null, null, defaultStatus);
        bookingRequestDto = new BookingRequestDto(0, 0, start, end);
    }

    @Test
    void toBookingDto() {
        booking.setId(id);
        assertEquals(bookingDto, BookingMapper.toBookingDto(booking));
    }

    @Test
    void toBooking() {
        assertEquals(booking, BookingMapper.toBooking(bookingRequestDto));
    }

    @Test
    void toBookingItemDto() {

    }
}