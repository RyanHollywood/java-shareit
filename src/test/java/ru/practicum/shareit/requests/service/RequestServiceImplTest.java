package ru.practicum.shareit.requests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.requests.RequestMapper;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.Request;
import ru.practicum.shareit.requests.storage.RequestRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    private Request request;

    @BeforeEach
    void reload() {
        request = new Request(1, "Description", 1, LocalDateTime.now());
    }

    @Test
    void create() {
        checkUser();
        when(requestRepository.save(any()))
                .thenReturn(request);
        assertEquals(RequestMapper.toRequestDto(request), requestService.create(RequestMapper.toRequestDto(request)));
    }

    @Test
    void getByRequester() {
        checkUser();
        when(requestRepository.findAllByRequesterId(anyLong()))
                .thenReturn(List.of(request));
        RequestDto requestDtoToCheck = RequestMapper.toRequestDto(request);
        requestDtoToCheck.setItems(Collections.emptyList());
        assertEquals(1, requestService.getByRequester(1).size());
        assertEquals(List.of(requestDtoToCheck), requestService.getByRequester(1));
    }

    @Test
    void getAll() {
        checkUser();
        when(requestRepository.findAll())
                .thenReturn(List.of(request));
        RequestDto requestDtoToCheck = RequestMapper.toRequestDto(request);
        requestDtoToCheck.setItems(Collections.emptyList());
        assertEquals(1, requestService.getAll(1, Optional.empty(), Optional.empty()).size());
        assertEquals(List.of(requestDtoToCheck), requestService.getAll(1, Optional.empty(), Optional.empty()));

    }

    @Test
    void getById() {
        checkUser();
        checkRequest();
        when(itemRepository.findAllByRequestId(anyLong()))
                .thenReturn(Collections.emptyList());
        RequestDto requestDtoToCheck = RequestMapper.toRequestDto(request);
        requestDtoToCheck.setItems(Collections.emptyList());
        assertEquals(requestDtoToCheck, requestService.getById(1, 1));
    }

    @Test
    void delete() {
        checkRequest();
        requestService.delete(1);
        Mockito.verify(requestRepository, times(1))
                .deleteById(anyLong());
    }

    private void checkUser() {
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
    }

    private void checkRequest() {
        when(requestRepository.findById(anyLong()))
                .thenReturn(Optional.of(request));
    }
}