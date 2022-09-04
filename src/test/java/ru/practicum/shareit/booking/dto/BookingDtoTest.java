package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookingDtoTest {

    private BookingDto bookingDto;
    private final long id = 1;
    private final LocalDateTime start = LocalDateTime.now();
    private final LocalDateTime end = LocalDateTime.now().plusHours(1);
    private final Item item = null;
    private final User booker = null;
    private final BookingStatus defaultStatus = BookingStatus.WAITING;


    @BeforeEach
    void setUp() {
        bookingDto = BookingDto.builder()
                .id(id)
                .start(start)
                .end(end)
                .booker(booker)
                .item(item)
                .status(defaultStatus)
                .build();
    }

    @Test
    void getId() {
        assertEquals(id, bookingDto.getId());
    }

    @Test
    void getStart() {
        assertEquals(start, bookingDto.getStart());
    }

    @Test
    void getEnd() {
        assertEquals(end, bookingDto.getEnd());
    }

    @Test
    void getItem() {
        assertEquals(item, bookingDto.getItem());
    }

    @Test
    void getBooker() {
        assertEquals(booker, bookingDto.getBooker());
    }

    @Test
    void getStatus() {
        assertEquals(defaultStatus, bookingDto.getStatus());
    }

    @Test
    void setId() {
        long newId = 2;
        bookingDto.setId(newId);
        assertEquals(newId, bookingDto.getId());
    }

    @Test
    void setStart() {
        LocalDateTime newStart = start.plusMinutes(30);
        bookingDto.setStart(newStart);
        assertEquals(newStart, bookingDto.getStart());
    }

    @Test
    void setEnd() {
        LocalDateTime newEnd = end.plusMinutes(30);
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
    void testEqualsAndHashCode() {
        BookingDto equalBooking = BookingDto.builder()
                .id(id)
                .start(start)
                .end(end)
                .booker(booker)
                .item(item)
                .status(defaultStatus)
                .build();
        assertEquals(equalBooking, bookingDto);
    }

    @Test
    void testNoEquals() {
        long newId = id + 1;
        BookingDto notEqualBooking = BookingDto.builder()
                .id(newId)
                .start(start)
                .end(end)
                .booker(booker)
                .item(item)
                .status(defaultStatus)
                .build();
        assertNotEquals(notEqualBooking, bookingDto);
    }

    @Test
    void noArgsConstructorTest() {
        BookingDto dtoWithEmptyConstructor = new BookingDto();
        dtoWithEmptyConstructor.setId(id);
        dtoWithEmptyConstructor.setStart(start);
        dtoWithEmptyConstructor.setEnd(end);
        dtoWithEmptyConstructor.setBooker(booker);
        dtoWithEmptyConstructor.setItem(item);
        dtoWithEmptyConstructor.setStatus(defaultStatus);

        BookingDto equalBooking = BookingDto.builder()
                .id(id)
                .start(start)
                .end(end)
                .booker(booker)
                .item(item)
                .status(defaultStatus)
                .build();

        assertEquals(equalBooking, dtoWithEmptyConstructor);
    }
}