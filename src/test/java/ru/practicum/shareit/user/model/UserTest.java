package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private final long id = 1;
    private final String name = "Name";
    private final String email = "Email@email.com";

    @BeforeEach
    void setUp() {
        user = new User(id, name, email);
    }

    @Test
    void getId() {
        assertEquals(id, user.getId());
    }

    @Test
    void getName() {
        assertEquals(name, user.getName());
    }

    @Test
    void getEmail() {
        assertEquals(email, user.getEmail());
    }

    @Test
    void setId() {
        long newId = 2;
        user.setId(newId);
        assertEquals(newId, user.getId());
    }

    @Test
    void setName() {
        String newName = "NewName";
        user.setName(newName);
        assertEquals(newName, user.getName());
    }

    @Test
    void setEmail() {
        String newEmail = "NewEmail";
        user.setEmail(newEmail);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    void testEquals() {
        User equalUser = new User(id, name, email);
        assertEquals(equalUser, user);
    }

    @Test
    void builder() {
        User userByBuilder = User.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
        assertEquals(userByBuilder, user);
    }
}