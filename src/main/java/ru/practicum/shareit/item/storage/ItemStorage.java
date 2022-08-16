package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemStorage {

    private Map<Long, Item> storage;
    private long idCounter;

    public ItemStorage() {
        storage = new HashMap<>();
        idCounter = 1;
    }

    public void add(Item item) {
        storage.put(item.getId(), item);
    }

    public Item get(long id) {
        return storage.get(id);
    }

    public Collection<Item> getAll() {
        return storage.values();
    }

    public long generateId() {
        return idCounter++;
    }

    public void delete(long id) {
        storage.remove(id);
    }

    public boolean contains(long id) {
        return storage.containsKey(id);
    }
}
