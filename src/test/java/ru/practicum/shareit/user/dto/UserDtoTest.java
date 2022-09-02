package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

    private UserDto userDto;
    private final long id = 1;
    private final String name = "Name";
    private final String email = "Email@email.com";

    @BeforeEach
    void setup() {
        userDto = new UserDto(id, name, email);
    }

    @Test
    void getId() {
        assertEquals(id, userDto.getId());
    }

    @Test
    void getName() {
        assertEquals(name, userDto.getName());
    }

    @Test
    void getEmail() {
        assertEquals(email, userDto.getEmail());
    }

    @Test
    void setId() {
        long newId = 2;
        userDto.setId(newId);
        assertEquals(newId, userDto.getId());
    }

    @Test
    void setName() {
        String newName = "NewName";
        userDto.setName(newName);
        assertEquals(newName, userDto.getName());
    }

    @Test
    void setEmail() {
        String newEmail = "NewEmail";
        userDto.setEmail(newEmail);
        assertEquals(newEmail, userDto.getEmail());
    }

    @Test
    void testEquals() {
        UserDto equalUser = new UserDto(id, name, email);
        assertEquals(equalUser, userDto);
    }
}