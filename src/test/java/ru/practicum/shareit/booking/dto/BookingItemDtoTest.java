package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingItemDtoTest {

    private BookingItemDto bookingItemDto;
    private final long id = 1;
    private final long bookerId = 1;

    @BeforeEach
    void setUp() {
        bookingItemDto = new BookingItemDto(id, bookerId);
    }

    @Test
    void getId() {
        assertEquals(id, bookingItemDto.getId());
    }

    @Test
    void getBookerId() {
        assertEquals(bookerId, bookingItemDto.getBookerId());
    }

    @Test
    void setId() {
        long newId = 2;
        bookingItemDto.setId(newId);
        assertEquals(newId, bookingItemDto.getId());
    }

    @Test
    void setBookerId() {
        long newBookerId = 2;
        bookingItemDto.setBookerId(newBookerId);
        assertEquals(newBookerId, bookingItemDto.getBookerId());
    }

    @Test
    void testEquals() {
        BookingItemDto equalBookingItemDto = new BookingItemDto(id, bookerId);
        assertEquals(equalBookingItemDto, bookingItemDto);
    }

    @Test
    void builder() {
        BookingItemDto dtoByBuilder = BookingItemDto.builder()
                .id(id)
                .bookerId(bookerId)
                .build();
        assertEquals(dtoByBuilder, bookingItemDto);
    }
}