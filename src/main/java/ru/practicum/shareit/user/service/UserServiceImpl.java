package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.errors.DuplicateEmailFound;
import ru.practicum.shareit.exceptions.errors.NoUserFound;
import ru.practicum.shareit.user.storage.UserStorage;
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

    @Override
    public UserDto create(UserDto userDto) {
        if (storage.checkDuplicateEmail(userDto.getEmail())) {
            log.warn("POST MAPPING UNSUCCESSFUL - DUPLICATE EMAIL " + userDto.getEmail() + " FOUND");
            throw new DuplicateEmailFound("DUPLICATE EMAIL " + userDto.getEmail() + " FOUND");
        }
        userDto.setId(storage.generateId());
        storage.add(UserMapper.toUser(userDto));
        log.debug("POST MAPPING SUCCESSFUL - USER ID:" + userDto.getId() + " CREATED");
        return userDto;
    }

    @Override
    public UserDto update(JsonNode object, long id) {
        if (object.has("name")) {
            storage.get(id).setName(object.get("name").textValue());
            log.debug("PATCH REQUEST SUCCESSFUL - USER ID:" + id + " NAME UPDATED");
        }
        if (object.has("email")) {
            if (storage.checkDuplicateEmail(object.get("email").textValue())) {
                log.warn("PATCH REQUEST UNSUCCESSFUL - USER ID:" + id + " UPDATING EMAIL ALREADY USED");
                throw new DuplicateEmailFound("DUPLICATE EMAIL FOUND");
            }
            storage.get(id).setEmail(object.get("email").textValue());
            log.debug("PATCH REQUEST SUCCESSFUL - USER ID:" + id + " EMAIL UPDATED");
        }
        log.debug("PATCH MAPPING SUCCESSFUL - USER UPDATED");
        return UserMapper.toUserDto(storage.get(id));
    }

    @Override
    public UserDto getUser(long id) {
        if (!storage.contains(id)) {
            log.warn("GET REQUEST UNSUCCESSFUL - USER ID:" + id + " NOT FOUND");
            throw new NoUserFound("NO USER ID:" + id + " NOT FOUND");
        }
        log.debug("GET REQUEST SUCCESSFUL - USER ID:" + id + " FOUND");
        return UserMapper.toUserDto(storage.get(id));
    }

    @Override
    public Collection<UserDto> getAll() {
        Set<UserDto> userDtoSet = new TreeSet<>((user1, user2) -> (int) (user1.getId() - user2.getId()));
        storage.getAll().stream()
                .map(UserMapper::toUserDto)
                .forEach(userDtoSet::add);
        log.debug("GET REQUEST SUCCESSFUL - " + userDtoSet.size() + " USERS FOUND");
        return userDtoSet;
    }

    @Override
    public void deleteUser(long id) {
        if (!storage.contains(id)) {
            log.warn("DELETE REQUEST UNSUCCESSFUL - USER ID:" + id + " NOT FOUND");
            throw new NoUserFound("NO USER ID:" + id + " NOT FOUND");
        }
        storage.delete(id);
        log.debug("DELETE REQUEST SUCCESSFUL - USER ID:" + id + " DELETED");
    }
}