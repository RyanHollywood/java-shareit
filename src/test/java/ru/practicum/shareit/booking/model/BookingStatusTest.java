package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BookingStatusTest {

    private final String[] STATUS_VALUES = new String[] {"WAITING", "APPROVED", "REJECTED", "CANCELLED"};

    @Test
    void values() {
        int counter = 0;
        for (BookingStatus status : BookingStatus.values()) {
            assertEquals(STATUS_VALUES[counter], String.valueOf(status));
            counter++;
        }
    }
}