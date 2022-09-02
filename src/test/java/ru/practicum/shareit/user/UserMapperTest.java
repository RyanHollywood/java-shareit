package ru.practicum.shareit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private User user;
    private UserDto userDto;
    private final long id = 1;
    private final String name = "Name";
    private final String email = "Email@email.com";

    @BeforeEach
    void setup() {
        user = new User(id, name, email);
        userDto = new UserDto(id, name, email);
    }

    @Test
    void toUser() {
        assertEquals(user, UserMapper.toUser(userDto));
    }

    @Test
    void toUserDto() {
        assertEquals(userDto, UserMapper.toUserDto(user));
    }
}