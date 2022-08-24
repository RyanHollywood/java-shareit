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

    private final long ID = 1;
    private final LocalDateTime START = LocalDateTime.now();
    private final LocalDateTime END = LocalDateTime.now().plusHours(1);
    private final Item ITEM = null;
    private final User BOOKER = null;
    private final BookingStatus DEFAULT_STATUS = BookingStatus.WAITING;
    private Booking booking;
    private BookingDto bookingDto;
    private BookingRequestDto bookingRequestDto;
    private BookingItemDto bookingItemDto;

    @BeforeEach
    void reload() {
        booking = new Booking(START, END, null, null, DEFAULT_STATUS);
        booking.setId(ID);
        bookingDto = new BookingDto(ID, START, END, null, null, DEFAULT_STATUS);
    }

    @Test
    void toBookingDto() {
        assertEquals(bookingDto, BookingMapper.toBookingDto(booking));
    }

    @Test
    void toBooking() {
    }

    @Test
    void toBookingItemDto() {
    }
}