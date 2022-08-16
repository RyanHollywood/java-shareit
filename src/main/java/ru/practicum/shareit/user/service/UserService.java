package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto update(JsonNode object, long id);

    UserDto getUser(long id);

    Collection<UserDto> getAll();

    void deleteUser(long id);
}