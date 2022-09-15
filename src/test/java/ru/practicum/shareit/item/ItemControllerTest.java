package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.JsonNode;
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
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @Mock
    private ItemServiceImpl itemService;
    @InjectMocks
    private ItemController itemController;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(itemController)
                .build();
        itemDto = new ItemDto(1, "Name", "Description", true, 1, 1L);
        mapper.findAndRegisterModules();
    }

    @Test
    void create() throws Exception {
        when(itemService.create(any(), anyLong()))
                .thenReturn(itemDto);
        mockMvc.perform((post("/items"))
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        itemDto.setName("NewName");
        itemDto.setDescription("NewDescription");
        itemDto.setAvailable(false);
        when(itemService.update(anyLong(), anyLong(), any()))
                .thenReturn(itemDto);
        JsonNode updateParameters = mapper.readTree("{\"name\":\"NewName\", \"description\": \"NewDescription\", \"available\":false}");
        mockMvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(updateParameters))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        when(itemService.getById(anyLong(), anyLong()))
                .thenReturn(itemDto);
        mockMvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        when(itemService.getAll(anyLong()))
                .thenReturn(List.of(itemDto));
        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isOk());
        Mockito.verify(itemService, times(1))
                .delete(anyLong());
    }

    @Test
    void search() throws Exception {
        when(itemService.search(anyString(), anyLong()))
                .thenReturn(List.of(itemDto));
        mockMvc.perform(get("/items/search")
                        .header("X-Sharer-User-Id", 1)
                        .param("text", "text")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addComment() throws Exception {
        CommentDto commentDto = new CommentDto(1, "Text", null, "AuthorName", LocalDateTime.now());
        mockMvc.perform(post("/items/1/comment")
                        .content(mapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}