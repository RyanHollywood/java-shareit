package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {

    private Item item;
    private ItemDto itemDto;
    private final long id = 1;
    private final String name = "Name";
    private final String description = "Description";
    private final boolean available = true;
    private final long ownerId = 1;
    private BookingItemDto lastBooking = null;
    private BookingItemDto nextBooking = null;
    private List<CommentDto> comments = null;
    private Optional<Long> requestId = Optional.of(1L);

    @BeforeEach
    void reload() {
        item = new Item(id, name, description, available, ownerId, requestId.get());
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
}