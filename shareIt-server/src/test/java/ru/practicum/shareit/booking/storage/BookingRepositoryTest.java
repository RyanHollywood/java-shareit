package ru.practicum.shareit.booking.storage;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    private User user;
    private Item item;
    private Booking booking;

    @BeforeEach
    void setUp() {
        user = new User(1, "Name", "Email@Email.com");
        item = new Item(1, "Name", "Description", true, 1, 1);
        booking = new Booking(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), item, user, BookingStatus.WAITING);
    }

    /*
    @Test
    @Rollback(false)
    void testFindByBookerIdOrderByStartDesc() {
        userRepository.save(user);
        itemRepository.save(item);
        bookingRepository.save(booking);
        assertEquals(booking, bookingRepository.findByBookerIdOrderByStartDesc(1).get(0));
    }

    @Test
    @Rollback(false)
    void testFindAllByItemIdInOrderByStartDesc() {
        userRepository.save(user);
        itemRepository.save(item);
        bookingRepository.save(booking);
        assertEquals(booking, bookingRepository.findAllByItemIdInOrderByStartDesc(List.of(1L)).get(0));
    }

     */
}