package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto create(ItemRequestDto itemDto, long requestorId);

    ItemRequestDto getById(long requestorId, long requestId);

    List<ItemRequestDto> getByRequestor(long requestorId);

    List<ItemRequestDto> getAll(long requestorId);

    void delete(long requestId);
}
