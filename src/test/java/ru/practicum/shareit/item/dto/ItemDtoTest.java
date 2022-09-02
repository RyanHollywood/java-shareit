package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.comment.dto.CommentDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDtoTest {

    private ItemDto itemDto;
    private final long id = 1;
    private final String name = "Name";
    private final String description = "Description";
    private final boolean available = true;
    private final long ownerId = 1;
    private BookingItemDto lastBooking = null;
    private BookingItemDto nextBooking = null;
    private List<CommentDto> comments = null;
    private Optional<Long> requestId = Optional.empty();

    @BeforeEach
    void setup() {
        itemDto = new ItemDto(id, name, description, available, ownerId, requestId);
    }


    @Test
    void getId() {
        assertEquals(id, itemDto.getId());
    }

    @Test
    void getName() {
        assertEquals(name, itemDto.getName());
    }

    @Test
    void getDescription() {
        assertEquals(description, itemDto.getDescription());
    }

    @Test
    void isAvailable() {
        assertEquals(available, itemDto.isAvailable());
    }

    @Test
    void getOwnerId() {
        assertEquals(ownerId, itemDto.getOwnerId());
    }

    @Test
    void getRequestId() {
        assertEquals(requestId, itemDto.getRequestId());
    }

    @Test
    void setId() {
        long newId = 2;
        itemDto.setId(newId);
        assertEquals(newId, itemDto.getId());
    }

    @Test
    void setName() {
        String newName = "NewName";
        itemDto.setName(newName);
        assertEquals(newName, itemDto.getName());
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        itemDto.setDescription(newDescription);
        assertEquals(newDescription, itemDto.getDescription());
    }

    @Test
    void setAvailable() {
        boolean newAvailable = false;
        itemDto.setAvailable(newAvailable);
        assertEquals(newAvailable, itemDto.isAvailable());
    }

    @Test
    void setOwnerId() {
        long newOwnerId = 2;
        itemDto.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, itemDto.getOwnerId());
    }

    @Test
    void testEquals() {
        ItemDto equalItem = new ItemDto(id, name, description, available, ownerId, requestId);
        assertEquals(equalItem, itemDto);
    }
}