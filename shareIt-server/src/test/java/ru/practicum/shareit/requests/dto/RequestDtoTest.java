package ru.practicum.shareit.requests.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestDtoTest {

    private RequestDto requestDto;
    private final long id = 1;
    private final String description = "Description";
    private final long requesterId = 1;
    private final List<ItemDto> items = null;
    private final LocalDateTime created = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        requestDto = new RequestDto(id, description, requesterId, created, items);
    }

    @Test
    void getId() {
        assertEquals(id, requestDto.getId());
    }

    @Test
    void getDescription() {
        assertEquals(description, requestDto.getDescription());
    }

    @Test
    void getRequesterId() {
        assertEquals(requesterId, requestDto.getRequesterId());
    }

    @Test
    void getCreated() {
        assertEquals(created, requestDto.getCreated());
    }

    @Test
    void setId() {
        long newId = 2;
        requestDto.setId(newId);
        assertEquals(newId, requestDto.getId());
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        requestDto.setDescription(newDescription);
        assertEquals(newDescription, requestDto.getDescription());
    }

    @Test
    void setRequesterId() {
        long newRequesterId = 2;
        requestDto.setRequesterId(newRequesterId);
        assertEquals(newRequesterId, requestDto.getRequesterId());
    }

    @Test
    void setCreated() {
        LocalDateTime newCreated = LocalDateTime.now().plusHours(1);
        requestDto.setCreated(newCreated);
        assertEquals(newCreated, requestDto.getCreated());
    }

    @Test
    void testEquals() {
        RequestDto equalRequest = RequestDto.builder()
                .id(id)
                .description(description)
                .requesterId(requesterId)
                .created(created)
                .items(items)
                .build();
        assertEquals(equalRequest, requestDto);
    }
}