package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private long id;

    @NotNull
    private Item item;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

    private User booker;

    private BookingStatus status;
}
