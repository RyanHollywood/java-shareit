package ru.practicum.shareit.requests.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class ItemRequestDto {
    private long id;

    @NotNull
    @NotBlank
    private String description;

    private long requestor;

    private LocalDateTime created;
}
