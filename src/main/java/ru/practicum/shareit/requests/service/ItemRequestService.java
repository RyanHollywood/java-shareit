package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;
import java.util.Optional;

public interface ItemRequestService {

    ItemRequestDto create(ItemRequestDto itemDto);

    ItemRequestDto getById(long requestId);

    List<ItemRequestDto> getByRequester(long requesterId);

    List<ItemRequestDto> getAll(long requesterId, Optional<Integer> from, Optional<Integer> size);

    void delete(long requestId);
}
