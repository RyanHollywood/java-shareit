package ru.practicum.shareit.requests.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.requests.ItemRequestMapper;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.storage.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;

@Service
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemRequestDto create(ItemRequestDto itemDto, long requestorId) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getByRequestor(long requestorId) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getAll(long requestorId) {
        return null;
    }

    @Override
    public ItemRequestDto getById(long requestorId, long requestId) {
        return null;
    }

    @Override
    public void delete(long requestId) {
        itemRequestRepository.deleteById(requestId);
    }
}
