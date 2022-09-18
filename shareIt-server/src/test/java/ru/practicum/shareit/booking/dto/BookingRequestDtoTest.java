package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestDtoTest {

    private BookingRequestDto bookingRequestDto;
    private final long bookerId = 1;
    private final long itemId = 1;
    private final LocalDateTime start = LocalDateTime.now();
    private final LocalDateTime end = LocalDateTime.now().plusHours(1);

    @BeforeEach
    void setUp() {
        bookingRequestDto = new BookingRequestDto(bookerId, itemId, start, end);
    }

    @Test
    void getBookerId() {
        assertEquals(bookerId, bookingRequestDto.getBookerId());
    }

    @Test
    void getItemId() {
        assertEquals(itemId, bookingRequestDto.getItemId());
    }

    @Test
    void getStart() {
        assertEquals(start, bookingRequestDto.getStart());
    }

    @Test
    void getEnd() {
        assertEquals(end, bookingRequestDto.getEnd());
    }

    @Test
    void setBookerId() {
        long newBookerId = 2;
        bookingRequestDto.setBookerId(newBookerId);
        assertEquals(newBookerId, bookingRequestDto.getBookerId());
    }

    @Test
    void setItemId() {
        long newItemId = 2;
        bookingRequestDto.setItemId(newItemId);
        assertEquals(newItemId, bookingRequestDto.getItemId());
    }

    @Test
    void setStart() {
        LocalDateTime newStart = start.plusMinutes(30);
        bookingRequestDto.setStart(newStart);
        assertEquals(newStart, bookingRequestDto.getStart());
    }

    @Test
    void setEnd() {
        LocalDateTime newEnd = end.plusMinutes(30);
        bookingRequestDto.setEnd(newEnd);
        assertEquals(newEnd, bookingRequestDto.getEnd());
    }

    @Test
    void testEquals() {
        BookingRequestDto equalBookingRequestDto = new BookingRequestDto(bookerId, itemId, start, end);
        assertEquals(equalBookingRequestDto, bookingRequestDto);
    }
}