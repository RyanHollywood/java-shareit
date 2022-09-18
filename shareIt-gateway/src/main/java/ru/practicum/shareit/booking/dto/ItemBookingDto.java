package ru.practicum.shareit.booking.dto;

import lombok.Data;

@Data
public class ItemBookingDto {
    private long id;
    private long bookerId;

    public ItemBookingDto(long id, long bookerId) {
        this.id = id;
        this.bookerId = bookerId;
    }
}
