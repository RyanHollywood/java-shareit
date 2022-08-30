package ru.practicum.shareit.requests.service;

import ru.practicum.shareit.requests.dto.RequestDto;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    RequestDto create(RequestDto itemDto);

    RequestDto getById(long requesterId, long requestId);

    List<RequestDto> getByRequester(long requesterId);

    List<RequestDto> getAll(long requesterId, Optional<Integer> from, Optional<Integer> size);

    void delete(long requestId);
}
