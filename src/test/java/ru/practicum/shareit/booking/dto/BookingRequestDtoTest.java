package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestDtoTest {

    private BookingRequestDto bookingRequestDto;
    private final long BOOKER_ID = 1;
    private final long ITEM_ID = 1;
    private final LocalDateTime START = LocalDateTime.now();
    private final LocalDateTime END = LocalDateTime.now().plusHours(1);

    @BeforeEach
    void reload() {
        bookingRequestDto = new BookingRequestDto(BOOKER_ID, ITEM_ID, START, END);
    }

    @Test
    void getBookerId() {
        assertEquals(BOOKER_ID, bookingRequestDto.getBookerId());
    }

    @Test
    void getItemId() {
        assertEquals(ITEM_ID, bookingRequestDto.getItemId());
    }

    @Test
    void getStart() {
        assertEquals(START, bookingRequestDto.getStart());
    }

    @Test
    void getEnd() {
        assertEquals(END, bookingRequestDto.getEnd());
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
        LocalDateTime newStart = START.plusMinutes(30);
        bookingRequestDto.setStart(newStart);
        assertEquals(newStart, bookingRequestDto.getStart());
    }

    @Test
    void setEnd() {
        LocalDateTime newEnd = END.plusMinutes(30);
        bookingRequestDto.setEnd(newEnd);
        assertEquals(newEnd, bookingRequestDto.getEnd());
    }

    @Test
    void testEquals() {
        BookingRequestDto equalBookingRequestDto = new BookingRequestDto(BOOKER_ID, ITEM_ID, START, END);
        assertEquals(equalBookingRequestDto, bookingRequestDto);
    }
}