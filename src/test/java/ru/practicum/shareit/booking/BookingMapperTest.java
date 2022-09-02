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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void setup() {
        booking = new Booking(start, end, item, booker, defaultStatus);
        bookingDto = new BookingDto(id, start, end, item, booker, defaultStatus);
        bookingRequestDto = new BookingRequestDto(0, 0, start, end);
        bookingItemDto = new BookingItemDto(id, id);
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
        booking.setId(id);
        User booker = User.builder()
                .id(1)
                .build();
        booking.setBooker(booker);
        assertEquals(bookingItemDto, BookingMapper.toBookingItemDto(booking));
    }
}