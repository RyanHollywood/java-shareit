package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto create(ItemDto itemDto, long ownerId);

    ItemDto update(long id, long ownerId, JsonNode object);

    ItemDto getById(long id, long ownerId);

    List<ItemDto> getAll(long ownerId);

    List<ItemDto> search(String text, long ownerId);

    void delete(long id);

    CommentDto addComment(long userId, long itemId, CommentDto commentDto);
}
