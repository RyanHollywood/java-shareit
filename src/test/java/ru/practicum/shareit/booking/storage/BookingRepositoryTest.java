package ru.practicum.shareit.booking.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void findByBookerIdOrderByStartDesc() {
        assertEquals(0, bookingRepository.findAllByItemIdInOrderByStartDesc(List.of()).size());
    }

    @Test
    void testFindByBookerIdOrderByStartDesc() {
    }

    @Test
    void findAllByItemIdInOrderByStartDesc() {
    }

    @Test
    void testFindAllByItemIdInOrderByStartDesc() {
    }
}