package ru.practicum.shareit.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exceptions.errors.NotFound;
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

    private final ObjectMapper mapper = new ObjectMapper();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(2, "User", "Email@email.com");
    }

    @Test
    void create() {
        when(userRepository.save(any()))
                .thenReturn(user);
        assertEquals(UserMapper.toUserDto(user), userService.create(UserMapper.toUserDto(user)));
    }

    @Test
    void update() throws JsonProcessingException {
        user.setName("NewName");
        user.setEmail("NewEmail@email.com");
        JsonNode updateParameters = mapper.readTree("{\"name\":\"NewName\", \"email\": \"NewEmail@email.com\"}");
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any()))
                .thenReturn(user);
        assertEquals(UserMapper.toUserDto(user), userService.update(1, updateParameters));

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        try {
            userService.update(1, updateParameters);
        } catch (NotFound exception) {
            assertEquals("User not found", exception.getMessage());
        }
    }

    @Test
    void getById() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        assertEquals(UserMapper.toUserDto(user), userService.getById(1));

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        try {
            userService.getById(1);
        } catch (NotFound exception) {
            assertEquals("User not found", exception.getMessage());
        }
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

        try {
            userService.delete(1);
        } catch (NotFound exception) {
            assertEquals("User not found", exception.getMessage());
        }
    }

    private void checkUser() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
    }
}