package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User(2, "User", "Email@email.com");
    }

    @Test
    void create() {
        when(userRepository.save(any()))
                .thenReturn(user);
        assertEquals(UserMapper.toUserDto(user), userService.create(UserMapper.toUserDto(user)));
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        assertEquals(UserMapper.toUserDto(user), userService.getById(1));
    }

    @Test
    void getAll() {
        when(userRepository.findAll())
                .thenReturn(List.of(user));
        assertEquals(1, userService.getAll().size());
        assertEquals(UserMapper.toUserDto(user), userService.getAll().get(0));
    }

    @Test
    void delete() {
        checkUser();
        userService.delete(1);
        Mockito.verify(userRepository, times(1))
                .deleteById(anyLong());
    }

    private void checkUser() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
    }
}