package ru.practicum.shareit.requests.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.errors.BadRequest;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.requests.ItemRequestMapper;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.storage.ItemRequestRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ItemRequestDto create(ItemRequestDto itemDto) {
        if (!userRepository.existsById(itemDto.getRequesterId())) {
            log.warn("Requestor not exists");
            throw new NotFound("Requestor not exists");
        }
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemDto)));
    }

    @Override
    public List<ItemRequestDto> getByRequester(long requesterId) {
        if (!userRepository.existsById(requesterId)) {
            log.warn("Requestor not exists");
            throw new NotFound("Requestor not exists");
        }
        return itemRequestRepository.findAllByRequesterId(requesterId).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAll(long requesterId, Optional<Integer> from, Optional<Integer> size) {
        if (from.isPresent() && size.isPresent()) {
            if (from.get() < 0 || size.get() <= 0) {
                throw new BadRequest("From and size parameters are negative or equal zero");
            }
            return itemRequestRepository.findAll(PageRequest.of(from.get(), size.get())).stream()
                    .map(ItemRequestMapper::toItemRequestDto)
                    .collect(Collectors.toList());
        }
        return itemRequestRepository.findAll().stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getById(long requestId) {
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.findById(requestId).orElseThrow(() -> {
            log.warn("Request not found");
            throw new NotFound("Request not found");
        }));
    }

    @Override
    public void delete(long requestId) {
        itemRequestRepository.deleteById(requestId);
    }
}
