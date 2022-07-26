package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    public ItemDto create(long ownerId, ItemDto itemDto) {
        return null;
    }

    public ItemDto update(long ownerId, long id, JsonNode body) {
        return null;
    }

    public ItemDto getItem(long ownerId, long id) {
        return null;
    }

    public Collection<ItemDto> getAll(long ownerId) {
        return null;
    }

    public Collection<ItemDto> search(long ownerId, String text) {
        return null;
    }

    public void delete(long id) {
    }
}
