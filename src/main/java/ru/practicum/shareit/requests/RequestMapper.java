package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.Request;

public class RequestMapper {
    public static RequestDto toItemRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .requesterId(request.getRequesterId())
                .created(request.getCreated())
                .build();
    }

    public static Request toItemRequest(RequestDto requestDto) {
        return Request.builder()
                .id(requestDto.getId())
                .description(requestDto.getDescription())
                .requesterId(requestDto.getRequesterId())
                .created(requestDto.getCreated())
                .build();
    }
}
