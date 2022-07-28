package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * // TODO .
 */

@Data
public class Booking {
    @NotNull
    private long id;

    @NonNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    @NotNull
    private long itemId;

    private long booker;
    private BookingStatus status;
}
