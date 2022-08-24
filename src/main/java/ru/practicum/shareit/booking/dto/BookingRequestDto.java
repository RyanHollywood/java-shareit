package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequestDto {
    private long bookerId;
    private long itemId;
    private LocalDateTime start;
    private LocalDateTime end;

    public BookingRequestDto(long bookerId) {
        this.bookerId = bookerId;
    }

    public BookingRequestDto(long bookerId, long itemId, LocalDateTime start, LocalDateTime end) {
        this.bookerId = bookerId;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
    }
}
