package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingDto create(BookingRequestDto requestDto);

    BookingDto update(long userId, long id, boolean parameter);

    BookingDto getById(long userId, long id);

    List<BookingDto> getAll(long userId, String state, Optional<Integer> from, Optional<Integer> size);

    List<BookingDto> getAllByOwner(long ownerId, String state, Optional<Integer> from, Optional<Integer> size);

    void delete(long id);
}
