package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private Booking booking;
    private BookingDto bookingDto;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(bookingController)
                .build();
        start = LocalDateTime.now().plusMinutes(30);
        end = LocalDateTime.now().plusHours(1);
        bookingRequestDto = new BookingRequestDto(1, 1, start, end);
        booking = new Booking(start, end, null, null, BookingStatus.WAITING);
        booking.setId(1);
        bookingDto = BookingMapper.toBookingDto(booking);
        mapper.findAndRegisterModules();
    }

    @Test
    void create() throws Exception {
        when(bookingService.create(any()))
                .thenReturn(bookingDto);
        mockMvc.perform((post("/bookings"))
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void updateApproved() throws Exception {
        bookingDto.setStatus(BookingStatus.APPROVED);
        when(bookingService.update(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(bookingDto);
        mockMvc.perform(patch("/bookings/1")
                        .param("approved", String.valueOf(true))
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void updateRejected() throws Exception {
        bookingDto.setStatus(BookingStatus.REJECTED);
        when(bookingService.update(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(bookingDto);
        mockMvc.perform(patch("/bookings/1")
                        .param("approved", String.valueOf(false))
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void getById() throws Exception {
        when(bookingService.getById(anyLong(), anyLong()))
                .thenReturn(bookingDto);
        mockMvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void getAllWithoutPaging() throws Exception {
        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(List.of(bookingDto));
        JSONArray bookings = new JSONArray();
        bookings.put(new JSONObject(mapper.writeValueAsString(bookingDto)));
        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(bookings)));
    }

    @Test
    void getAllWithPaging() throws Exception {
        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(List.of(bookingDto));
        JSONArray bookings = new JSONArray();
        bookings.put(new JSONObject(mapper.writeValueAsString(bookingDto)));
        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 2)
                        .param("from", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(bookings)));
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
                .thenReturn(List.of(bookingDto));
        JSONArray bookings = new JSONArray();
        bookings.put(new JSONObject(mapper.writeValueAsString(bookingDto)));
        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(bookings)));
        ;
    }

    @Test
    void getAllByOwnerWithPaging() throws Exception {
        when(bookingService.getAllByOwner(anyLong(), anyString(), any(), any()))
                .thenReturn(List.of(bookingDto));
        JSONArray bookings = new JSONArray();
        bookings.put(new JSONObject(mapper.writeValueAsString(bookingDto)));
        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .param("from", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(bookings)));
        ;
    }
}