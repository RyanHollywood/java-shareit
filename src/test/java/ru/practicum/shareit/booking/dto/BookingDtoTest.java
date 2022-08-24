package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingDtoTest {

    private BookingDto bookingDto;
    private final long ID = 1;
    private final LocalDateTime START = LocalDateTime.now();
    private final LocalDateTime END = LocalDateTime.now().plusHours(1);
    private final Item ITEM = null;
    private final User BOOKER = null;
    private final BookingStatus DEFAULT_STATUS = BookingStatus.WAITING;


    @BeforeEach
    void reload() {
        bookingDto = BookingDto.builder()
                .id(ID)
                .start(START)
                .end(END)
                .booker(BOOKER)
                .item(ITEM)
                .status(DEFAULT_STATUS)
                .build();
    }

    @Test
    void getId() {
        assertEquals(ID, bookingDto.getId());
    }

    @Test
    void getStart() {
        assertEquals(START, bookingDto.getStart());
    }

    @Test
    void getEnd() {
        assertEquals(END, bookingDto.getEnd());
    }

    @Test
    void getItem() {
        assertEquals(ITEM, bookingDto.getItem());
    }

    @Test
    void getBooker() {
        assertEquals(BOOKER, bookingDto.getBooker());
    }

    @Test
    void getStatus() {
        assertEquals(DEFAULT_STATUS, bookingDto.getStatus());
    }

    @Test
    void setId() {
        long newId = 2;
        bookingDto.setId(newId);
        assertEquals(newId, bookingDto.getId());
    }

    @Test
    void setStart() {
        LocalDateTime newStart = START.plusMinutes(30);
        bookingDto.setStart(newStart);
        assertEquals(newStart, bookingDto.getStart());
    }

    @Test
    void setEnd() {
        LocalDateTime newEnd = END.plusMinutes(30);
        bookingDto.setEnd(newEnd);
        assertEquals(newEnd, bookingDto.getEnd());
    }

    @Test
    void setItem() {
        Item newItem = Item.builder()
                .id(1)
                .name("name")
                .description("description")
                .available(true)
                .ownerId(1)
                .build();
        bookingDto.setItem(newItem);
        assertEquals(newItem, bookingDto.getItem());
    }

    @Test
    void setBooker() {
        User newBooker = User.builder()
                .id(1)
                .name("name")
                .email("email")
                .build();
        bookingDto.setBooker(newBooker);
        assertEquals(newBooker, bookingDto.getBooker());
    }

    @Test
    void setStatus() {
        BookingStatus newStatus = BookingStatus.APPROVED;
        bookingDto.setStatus(newStatus);
        assertEquals(newStatus, bookingDto.getStatus());
    }

    @Test
    void testEquals() {
        BookingDto equalBooking = BookingDto.builder()
                .id(ID)
                .start(START)
                .end(END)
                .booker(BOOKER)
                .item(ITEM)
                .status(DEFAULT_STATUS)
                .build();
        equalBooking.setId(ID);
        assertEquals(equalBooking, bookingDto);
    }
}