package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    private Booking booking;
    private final long ID = 1;
    private final LocalDateTime START = LocalDateTime.now();
    private final LocalDateTime END = LocalDateTime.now().plusHours(1);
    private final Item ITEM = null;
    private final User BOOKER = null;
    private final BookingStatus DEFAULT_STATUS = BookingStatus.WAITING;


    @BeforeEach
    void reload() {
        booking = new Booking(START, END, ITEM, BOOKER, DEFAULT_STATUS);
        booking.setId(ID);
    }

    @Test
    void getId() {
        assertEquals(ID, booking.getId());
    }

    @Test
    void getStart() {
        assertEquals(START, booking.getStart());
    }

    @Test
    void getEnd() {
        assertEquals(END, booking.getEnd());
    }

    @Test
    void getItem() {
        assertEquals(ITEM, booking.getItem());
    }

    @Test
    void getBooker() {
        assertEquals(BOOKER, booking.getBooker());
    }

    @Test
    void getStatus() {
        assertEquals(DEFAULT_STATUS, booking.getStatus());
    }

    @Test
    void setId() {
        long newId = 2;
        booking.setId(newId);
        assertEquals(newId, booking.getId());
    }

    @Test
    void setStart() {
        LocalDateTime newStart = START.plusMinutes(30);
        booking.setStart(newStart);
        assertEquals(newStart, booking.getStart());
    }

    @Test
    void setEnd() {
        LocalDateTime newEnd = END.plusMinutes(30);
        booking.setEnd(newEnd);
        assertEquals(newEnd, booking.getEnd());
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
        booking.setItem(newItem);
        assertEquals(newItem, booking.getItem());
    }

    @Test
    void setBooker() {
        User newBooker = User.builder()
                .id(1)
                .name("name")
                .email("email")
                .build();
        booking.setBooker(newBooker);
        assertEquals(newBooker, booking.getBooker());
    }

    @Test
    void setStatus() {
        BookingStatus newStatus = BookingStatus.APPROVED;
        booking.setStatus(newStatus);
        assertEquals(newStatus, booking.getStatus());
    }

    @Test
    void testEquals() {
        Booking equalBooking = new Booking(START, END, ITEM, BOOKER, DEFAULT_STATUS);
        equalBooking.setId(ID);
        assertEquals(equalBooking, booking);
    }
}