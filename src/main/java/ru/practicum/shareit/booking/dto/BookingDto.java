package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NonNull;
import ru.practicum.shareit.booking.model.BookingStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * // TODO .
 */

@Data
public class BookingDto {
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
