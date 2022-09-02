package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    private Booking booking;
    private final long id = 1;
    private final LocalDateTime start = LocalDateTime.now();
    private final LocalDateTime end = LocalDateTime.now().plusHours(1);
    private final Item item = null;
    private final User booker = null;
    private final BookingStatus defaultStatus = BookingStatus.WAITING;


    @BeforeEach
    void setup() {
        booking = new Booking(start, end, item, booker, defaultStatus);
        booking.setId(id);
    }

    @Test
    void getId() {
        assertEquals(id, booking.getId());
    }

    @Test
    void getStart() {
        assertEquals(start, booking.getStart());
    }

    @Test
    void getEnd() {
        assertEquals(end, booking.getEnd());
    }

    @Test
    void getItem() {
        assertEquals(item, booking.getItem());
    }

    @Test
    void getBooker() {
        assertEquals(booker, booking.getBooker());
    }

    @Test
    void getStatus() {
        assertEquals(defaultStatus, booking.getStatus());
    }

    @Test
    void setId() {
        long newId = 2;
        booking.setId(newId);
        assertEquals(newId, booking.getId());
    }

    @Test
    void setStart() {
        LocalDateTime newStart = start.plusMinutes(30);
        booking.setStart(newStart);
        assertEquals(newStart, booking.getStart());
    }

    @Test
    void setEnd() {
        LocalDateTime newEnd = end.plusMinutes(30);
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
        Booking equalBooking = new Booking(start, end, item, booker, defaultStatus);
        equalBooking.setId(id);
        assertEquals(equalBooking, booking);
    }
}