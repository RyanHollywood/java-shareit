package ru.practicum.shareit.requests.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RequestDto {
    private long id;

    @NotNull
    @NotBlank
    private String description;

    private long requesterId;

    private LocalDateTime created;

    private List<ItemDto> items;
}
