package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.Request;

public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .requesterId(request.getRequesterId())
                .created(request.getCreated())
                .build();
    }

    public static Request toRequest(RequestDto requestDto) {
        return Request.builder()
                .id(requestDto.getId())
                .description(requestDto.getDescription())
                .requesterId(requestDto.getRequesterId())
                .created(requestDto.getCreated())
                .build();
    }
}
