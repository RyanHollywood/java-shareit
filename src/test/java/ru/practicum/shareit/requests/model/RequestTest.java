package ru.practicum.shareit.requests.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    private Request request;
    private final long id = 1;
    private final String description = "Description";
    private final long requesterId = 1;
    private final LocalDateTime created = LocalDateTime.now();

    @BeforeEach
    void setup() {
        request = new Request(id, description, requesterId, created);
    }

    @Test
    void getId() {
        assertEquals(id, request.getId());
    }

    @Test
    void getDescription() {
        assertEquals(description, request.getDescription());
    }

    @Test
    void getRequesterId() {
        assertEquals(requesterId, request.getRequesterId());
    }

    @Test
    void getCreated() {
        assertEquals(created, request.getCreated());
    }

    @Test
    void setId() {
        long newId = 2;
        request.setId(newId);
        assertEquals(newId, request.getId());
    }

    @Test
    void setDescription() {
        String newDescription = "NewDescription";
        request.setDescription(newDescription);
        assertEquals(newDescription, request.getDescription());
    }

    @Test
    void setRequesterId() {
        long newRequesterId = 2;
        request.setRequesterId(newRequesterId);
        assertEquals(newRequesterId, request.getRequesterId());
    }

    @Test
    void setCreated() {
        LocalDateTime newCreated = LocalDateTime.now().plusHours(1);
        request.setCreated(newCreated);
        assertEquals(newCreated, request.getCreated());
    }

    @Test
    void testEquals() {
        Request equalRequest = Request.builder()
                .id(id)
                .description(description)
                .requesterId(requesterId)
                .created(created)
                .build();
        assertEquals(equalRequest, request);
    }
}