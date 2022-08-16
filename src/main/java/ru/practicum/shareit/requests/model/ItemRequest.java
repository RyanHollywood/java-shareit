package ru.practicum.shareit.requests.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * // TODO .
 */

@Data
public class ItemRequest {
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
