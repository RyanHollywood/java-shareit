package ru.practicum.shareit.exceptions.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundTest {
    private NotFound notFound;
    private String message;

    @BeforeEach
    void setUp() {
        message = "Not found";
        notFound = new NotFound(message);
    }

    @Test
    void constructorTest() {
        assertEquals(message, notFound.getMessage());
    }
}