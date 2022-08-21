package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("");
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto update(long id, JsonNode object) {
        User userToUpdate = repository.findById(id).orElseThrow(() -> {
            log.warn("");
            return new NotFound("");});
        if (object.has("name")) {
            userToUpdate.setName(object.get("name").textValue());
            log.debug("");
        }
        if (object.has("email")) {
            userToUpdate.setEmail(object.get("email").textValue());
            log.debug("");
        }
        return UserMapper.toUserDto(repository.save(userToUpdate));
    }

    @Override
    public UserDto getById(long id) {
        User userToGet = repository.findById(id).orElseThrow(() -> {
            log.warn("");
            return new NotFound("");});
        log.debug("");
        return UserMapper.toUserDto(userToGet);
    }

    @Override
    public List<UserDto> getAll() {
        log.debug("");
        return repository.findAll().stream()
                .map(UserMapper::toUserDto)
                .sorted(Comparator.comparingLong(UserDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        log.debug("");
        repository.deleteById(id);
    }
}