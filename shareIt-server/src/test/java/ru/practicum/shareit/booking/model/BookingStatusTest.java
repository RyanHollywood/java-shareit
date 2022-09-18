package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingStatusTest {

    private final String[] statusValues = new String[] {"WAITING", "APPROVED", "REJECTED", "CANCELLED"};

    @Test
    void values() {
        int counter = 0;
        for (BookingStatus status : BookingStatus.values()) {
            assertEquals(statusValues[counter], String.valueOf(status));
            counter++;
        }
    }
}