package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingServiceImpl bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();
    private BookingRequestDto bookingRequestDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .build();
        bookingRequestDto = new BookingRequestDto(1, 1, LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusHours(1));
        mapper.findAndRegisterModules();
    }

    @Test
    void create() throws Exception {
        when(bookingService.create(any()))
                .thenReturn(null);
        mockMvc.perform((post("/bookings"))
                        .content(mapper.writeValueAsString(bookingRequestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        when(bookingService.update(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(null);
        mockMvc.perform(patch("/bookings/1")
                        .param("approved", String.valueOf(true))
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        when(bookingService.getById(anyLong(), anyLong()))
                .thenReturn(null);
        mockMvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllWithoutPaging() throws Exception {
        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(null);
        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllWithPaging() throws Exception {
        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(null);
        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 2)
                        .param("from", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isOk());
        Mockito.verify(bookingService, times(1))
                .delete(anyLong());
    }

    @Test
    void getAllByOwnerWithoutPaging() throws Exception {
        when(bookingService.getAllByOwner(anyLong(), anyString(), any(), any()))
                .thenReturn(null);
        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllByOwnerWithPaging() throws Exception {
        when(bookingService.getAllByOwner(anyLong(), anyString(), any(), any()))
                .thenReturn(null);
        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .param("from", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}