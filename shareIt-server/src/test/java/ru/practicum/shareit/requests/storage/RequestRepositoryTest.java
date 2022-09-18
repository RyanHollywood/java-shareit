package ru.practicum.shareit.requests.storage;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.requests.model.Request;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase
class RequestRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User user;
    private Request request;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Name", "Email@Email.com");
        request = new Request(1L, "Description", 1, LocalDateTime.now());
    }

    /*
    @Test
    @Rollback(false)
    void findAllByRequesterId() {
        userRepository.save(user);
        requestRepository.save(request);
        assertEquals(request, requestRepository.findAllByRequesterId(1).get(0));
    }
     */
}