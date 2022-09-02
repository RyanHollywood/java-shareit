package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {

    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllByOwner() {
    }
}