package ru.practicum.shareit.comment.dto;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @NotNull
    private String text;
    private ItemDto itemDto;
    private String authorName;
    private LocalDateTime created;

    public CommentDto(long id, String text, ItemDto itemDto, String authorName, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.itemDto = itemDto;
        this.authorName = authorName;
        this.created = created;
    }
}
