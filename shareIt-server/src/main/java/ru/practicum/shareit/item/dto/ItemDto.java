package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.comment.dto.CommentDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDto {
    private long id;

    private String name;


    private String description;


    private Boolean available;

    private long ownerId;
    private BookingItemDto lastBooking;
    private BookingItemDto nextBooking;
    private List<CommentDto> comments;
    private Long requestId;

    public ItemDto(long id, String name, String description, Boolean available, long ownerId, Long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.ownerId = ownerId;
        this.requestId = requestId;
    }
}
