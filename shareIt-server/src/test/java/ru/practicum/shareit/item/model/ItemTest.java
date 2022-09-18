package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    private Item item;
    private final long id = 1;
    private final String name = "Name";
    private final String description = "Description";
    private final boolean available = true;
    private final long ownerId = 1;
    private final long requestId = 1;

    @BeforeEach
    void setUp() {
        item = new Item(id, name, description, available, ownerId, requestId);
    }

    @Test
    void getId() {
        assertEquals(id, item.getId());
    }

    @Test
    void getName() {
        assertEquals(name, item.getName());
    }

    @Test
    void getDescription() {
        assertEquals(description, item.getDescription());
    }

    @Test
    void isAvailable() {
        assertEquals(available, item.isAvailable());
    }

    @Test
    void getOwnerId() {
        assertEquals(ownerId, item.getOwnerId());
    }

    @Test
    void getRequestId() {
        assertEquals(requestId, item.getRequestId());
    }

    @Test
    void setId() {
        long newId = 2;
        item.setId(newId);
        assertEquals(newId, item.getId());
    }

    @Test
    void setName() {
        String newName = "NewName";
        item.setName(newName);
        assertEquals(newName, item.getName());
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        item.setDescription(newDescription);
        assertEquals(newDescription, item.getDescription());
    }

    @Test
    void setAvailable() {
        boolean newAvailable = false;
        item.setAvailable(newAvailable);
        assertEquals(newAvailable, item.isAvailable());
    }

    @Test
    void setOwnerId() {
        long newOwnerId = 2;
        item.setOwnerId(newOwnerId);
        assertEquals(newOwnerId, item.getOwnerId());
    }

    @Test
    void testEquals() {
        Item equalItem = new Item(id, name, description, available, ownerId, requestId);
        assertEquals(equalItem, item);
    }

    @Test
    void builderTest() {
        Item itemByBuilder = Item.builder()
                .id(id)
                .name(name)
                .description(description)
                .available(available)
                .ownerId(ownerId)
                .requestId(requestId)
                .build();
        assertEquals(itemByBuilder, item);
    }

    @Test
    void constructorTest() {
        Item equalItem = new Item(id, name, description, available, ownerId);
        equalItem.setRequestId(requestId);
        assertEquals(item, equalItem);
    }
}