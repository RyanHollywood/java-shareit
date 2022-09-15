package ru.practicum.shareit.exceptions.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConflictTest {
    private Conflict conflict;
    private String message;

    @BeforeEach
    void setUp() {
        message = "Conflict";
        conflict = new Conflict(message);
    }

    @Test
    void constructorTest() {
        assertEquals(message, conflict.getMessage());
    }
}