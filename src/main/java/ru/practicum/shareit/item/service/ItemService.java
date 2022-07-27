package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {

    ItemDto create(long ownerId, ItemDto itemDto);

    ItemDto update(long ownerId, long id, JsonNode object);

    ItemDto getItem(long ownerId, long id);

    Collection<ItemDto> getAll(long ownerId);

    Collection<ItemDto> search(long ownerId, String text);

    void delete(long id);
}
