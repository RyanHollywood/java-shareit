package ru.practicum.shareit.exceptions.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadRequestTest {
    private BadRequest badRequest;
    private String message;

    @BeforeEach
    void setup() {
        message = "Bad request";
        badRequest = new BadRequest(message);
    }

    @Test
    void constructorTest() {
        assertEquals(message, badRequest.getMessage());
    }
}