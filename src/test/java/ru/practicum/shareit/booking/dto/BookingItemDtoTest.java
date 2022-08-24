package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingItemDtoTest {

    private BookingItemDto bookingItemDto;
    private final long ID = 1;
    private final long BOOKER_ID = 1;

    @BeforeEach
    void reload() {
        bookingItemDto = new BookingItemDto(ID, BOOKER_ID);
    }

    @Test
    void getId() {
        assertEquals(ID, bookingItemDto.getId());
    }

    @Test
    void getBookerId() {
        assertEquals(BOOKER_ID, bookingItemDto.getBookerId());
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
        BookingItemDto equalBookingItemDto = new BookingItemDto(ID, BOOKER_ID);
        assertEquals(equalBookingItemDto, bookingItemDto);
    }

    @Test
    void builder() {
        BookingItemDto dtoByBuilder = BookingItemDto.builder()
                .id(ID)
                .bookerId(BOOKER_ID)
                .build();
        assertEquals(dtoByBuilder, bookingItemDto);
    }
}