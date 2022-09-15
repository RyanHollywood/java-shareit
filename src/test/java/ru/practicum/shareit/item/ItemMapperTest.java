package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {

    private final long id = 1;
    private final String name = "Name";
    private final String description = "Description";
    private final boolean available = true;
    private final long ownerId = 1;
    private Item item;
    private ItemDto itemDto;
    private BookingItemDto lastBooking = null;
    private BookingItemDto nextBooking = null;
    private List<CommentDto> comments = null;
    private Long requestId = 1L;

    @BeforeEach
    void setUp() {
        item = new Item(id, name, description, available, ownerId, requestId);
        itemDto = new ItemDto(id, name, description, available, ownerId, requestId);
    }

    @Test
    void toItemDto() {
        assertEquals(itemDto, ItemMapper.toItemDto(item));
    }

    @Test
    void toItem() {
        assertEquals(item, ItemMapper.toItem(itemDto));
    }

    @Test
    void builderTest() {
        itemDto.setRequestId(null);
        item = new Item(id, name, description, available, ownerId);
        assertEquals(item, ItemMapper.toItem(itemDto));
    }
}