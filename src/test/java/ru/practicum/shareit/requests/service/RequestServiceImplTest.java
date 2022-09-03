package ru.practicum.shareit.requests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exceptions.errors.NotFound;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void setUp() {
        request = new Request(1, "Description", 1, LocalDateTime.now());
    }

    @Test
    void create() {
        checkUserOk();
        when(requestRepository.save(any()))
                .thenReturn(request);
        assertEquals(RequestMapper.toRequestDto(request), requestService.create(RequestMapper.toRequestDto(request)));

        checkUserNotExist();
        try {
            requestService.create(RequestMapper.toRequestDto(request));
        } catch (NotFound exception) {
            assertEquals("Requester not exists", exception.getMessage());
        }
    }

    @Test
    void getByRequester() {
        checkUserOk();
        when(requestRepository.findAllByRequesterId(anyLong()))
                .thenReturn(List.of(request));
        RequestDto requestDtoToCheck = RequestMapper.toRequestDto(request);
        requestDtoToCheck.setItems(Collections.emptyList());
        assertEquals(1, requestService.getByRequester(1).size());
        assertEquals(List.of(requestDtoToCheck), requestService.getByRequester(1));
    }

    @Test
    void getAll() {
        checkUserOk();
        when(requestRepository.findAll())
                .thenReturn(List.of(request));
        RequestDto requestDtoToCheck = RequestMapper.toRequestDto(request);
        requestDtoToCheck.setItems(Collections.emptyList());
        assertEquals(1, requestService.getAll(1, Optional.empty(), Optional.empty()).size());
        assertEquals(List.of(requestDtoToCheck), requestService.getAll(1, Optional.empty(), Optional.empty()));

    }

    @Test
    void getById() {
        checkUserOk();
        checkRequestOk();
        when(itemRepository.findAllByRequestId(anyLong()))
                .thenReturn(Collections.emptyList());
        RequestDto requestDtoToCheck = RequestMapper.toRequestDto(request);
        requestDtoToCheck.setItems(Collections.emptyList());
        assertEquals(requestDtoToCheck, requestService.getById(1, 1));


    }

    @Test
    void delete() {
        checkRequestOk();
        requestService.delete(1);
        Mockito.verify(requestRepository, times(1))
                .deleteById(anyLong());
    }

    private void checkUserOk() {
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
    }

    private void checkUserNotExist() {
        when(userRepository.existsById(anyLong()))
                .thenReturn(false);
    }

    private void checkRequestOk() {
        when(requestRepository.findById(anyLong()))
                .thenReturn(Optional.of(request));
    }
}