package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.errors.DuplicateEmailFound;
import ru.practicum.shareit.storage.UserStorage;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserStorage storage;

    @Autowired
    public UserServiceImpl(UserStorage storage) {
        this.storage = storage;
    }

    public UserDto create(UserDto userDto) {
        if (storage.checkDuplicateEmail(userDto.getEmail())) {
            log.warn("POST MAPPING UNSUCCESSFUL - DUPLICATE EMAIL FOUND");
            throw new DuplicateEmailFound("DUPLICATE EMAIL FOUND");
        }
        userDto.setId(storage.generateId());
        storage.add(UserMapper.toUser(userDto));
        log.debug("POST MAPPING SUCCESSFUL - USER CREATED");
        return userDto;
    }

    public UserDto update(JsonNode object, long id) {
        if (object.has("name")) {
            storage.get(id).setName(object.get("name").textValue());
        }
        if (object.has("email")) {
            if (storage.checkDuplicateEmail(object.get("email").textValue())) {
                log.warn("PATCH MAPPING UNSUCCESSFUL - DUPLICATE EMAIL FOUND");
                throw new DuplicateEmailFound("DUPLICATE EMAIL FOUND");
            }
            storage.get(id).setEmail(object.get("email").textValue());
        }
        log.debug("PATCH MAPPING SUCCESSFUL - USER UPDATED");
        return UserMapper.toUserDto(storage.get(id));
    }

    public UserDto getUser(long id) {
        return UserMapper.toUserDto(storage.get(id));
    }

    public Collection<UserDto> getAll() {
        Set<UserDto> userDtoSet= new TreeSet<>((user1, user2) -> (int) (user1.getId() - user2.getId()));
        storage.getAll().stream()
                .map(UserMapper::toUserDto)
                .forEach(userDtoSet::add);
        return userDtoSet;
    }

    public void deleteUser(long id) {
        storage.delete(id);
    }
}