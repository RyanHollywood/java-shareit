package ru.practicum.shareit.item.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */

@Data
public class ItemDto {
    private long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Boolean available;

    private long ownerId;

    public ItemDto(long id, String name, String description, Boolean available, long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.ownerId = ownerId;
    }
}
