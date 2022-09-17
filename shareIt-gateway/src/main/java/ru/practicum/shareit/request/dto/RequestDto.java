package ru.practicum.shareit.request.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private long id;
    private String description;
    private long requester;
    private LocalDateTime created;
    @ToString.Exclude
    private List<ItemDto> itemDtos = new ArrayList<>();
}
