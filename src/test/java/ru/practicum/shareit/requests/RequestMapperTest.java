package ru.practicum.shareit.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.Request;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestMapperTest {

    private RequestDto requestDto;
    private Request request;
    private final long id = 1;
    private final String description = "Description";
    private final long requesterId = 1;
    private final List<ItemDto> items = null;
    private final LocalDateTime created = LocalDateTime.now();

    @BeforeEach
    void reload() {
        requestDto = new RequestDto(id, description, requesterId, created, items);
        request = new Request(id, description, requesterId, created);
    }

    @Test
    void toRequestDto() {
        assertEquals(requestDto, RequestMapper.toRequestDto(request));
    }

    @Test
    void toRequest() {
        assertEquals(request, RequestMapper.toRequest(requestDto));
    }
}