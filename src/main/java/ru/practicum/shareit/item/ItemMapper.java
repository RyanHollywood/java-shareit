package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Optional;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getOwnerId(),
                Optional.of(item.getRequestId()));
    }

    public static Item toItem(ItemDto itemDto) {
        if (itemDto.getRequestId().isPresent()) {
            return Item.builder()
                    .id(itemDto.getId())
                    .name(itemDto.getName())
                    .description(itemDto.getDescription())
                    .available(itemDto.isAvailable())
                    .ownerId(itemDto.getOwnerId())
                    .requestId(itemDto.getRequestId().get())
                    .build();
        }
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.isAvailable())
                .ownerId(itemDto.getOwnerId())
                .build();
    }
}