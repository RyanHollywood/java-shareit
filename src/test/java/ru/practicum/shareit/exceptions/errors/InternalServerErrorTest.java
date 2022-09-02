package ru.practicum.shareit.exceptions.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InternalServerErrorTest {
    private InternalServerError internalServerError;
    private String message;

    @BeforeEach
    void setup() {
        message = "InternalServerError";
        internalServerError = new InternalServerError(message);
    }

    @Test
    void constructorTest() {
        assertEquals(message, internalServerError.getMessage());
    }
}