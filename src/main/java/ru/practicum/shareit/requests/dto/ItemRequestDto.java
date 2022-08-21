package ru.practicum.shareit.requests.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ItemRequestDto {
    @NotNull
    private long id;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private long requestor;

    @NotNull
    private LocalDateTime created;
}
