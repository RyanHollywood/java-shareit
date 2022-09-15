package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(long id, JsonNode object);

    UserDto getById(long id);

    List<UserDto> getAll();

    void delete(long id);
}