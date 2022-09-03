package ru.practicum.shareit.requests;

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
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.service.RequestServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    @Mock
    private RequestServiceImpl requestService;

    @InjectMocks
    private RequestController requestController;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();
    private RequestDto requestDto;

    @BeforeEach
    void setUpd() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(requestController)
                .build();
        requestDto = new RequestDto(1, "Description", 1, LocalDateTime.now(), null);
        mapper.findAndRegisterModules();
    }

    @Test
    void create() throws Exception {
        when(requestService.create(any()))
                .thenReturn(null);
        mockMvc.perform((post("/requests"))
                        .content(mapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getByRequester() {
    }

    @Test
    void getAll() throws Exception {
        when(requestService.getAll(anyLong(), any(), any()))
                .thenReturn(List.of(requestDto));
        mockMvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() {
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/requests/1"))
                .andExpect(status().isOk());
        Mockito.verify(requestService, times(1))
                .delete(anyLong());
    }
}