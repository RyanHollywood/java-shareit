package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto create(RequestDto itemDto);

    RequestDto getById(long requesterId, long requestId);

    List<RequestDto> getByRequester(long requesterId);

    List<RequestDto> getAll(long requesterId, Integer from, Integer size);

    void delete(long requestId);
}
