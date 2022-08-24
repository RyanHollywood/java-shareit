package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto create(ItemRequestDto itemDto, long requestor);

    ItemRequestDto getById(long id, long requestor);

    List<ItemRequestDto> getAll(long requestor);

    void delete(long id);
}
