package ru.practicum.shareit.requests.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.errors.BadRequest;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.requests.RequestMapper;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.model.Request;
import ru.practicum.shareit.requests.storage.RequestRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public RequestDto create(RequestDto requestDto) {
        if (!userRepository.existsById(requestDto.getRequesterId())) {
            log.warn("Requester not exists");
            throw new NotFound("Requester not exists");
        }
        log.debug("");
        return RequestMapper.toRequestDto(requestRepository.save(RequestMapper.toRequest(requestDto)));
    }

    @Override
    public List<RequestDto> getByRequester(long requesterId) {
        if (!userRepository.existsById(requesterId)) {
            log.warn("Requester not exists");
            throw new NotFound("Requester not exists");
        }
        log.debug("");
        return requestRepository.findAllByRequesterId(requesterId).stream()
                .map(RequestMapper::toRequestDto)
                .map(this::setItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getAll(long requesterId, Optional<Integer> from, Optional<Integer> size) {
        if (!userRepository.existsById(requesterId)) {
            log.warn("Requester not found");
            throw new NotFound("Requester not found");
        }
        if (from.isPresent() && size.isPresent()) {
            if (from.get() < 0 || size.get() <= 0) {
                throw new BadRequest("From and size parameters are negative or equal zero");
            }
            log.debug("");
            return requestRepository.findAll(PageRequest.of(from.get(), size.get())).stream()
                    .filter(request -> request.getRequesterId() != requesterId)
                    .map(RequestMapper::toRequestDto)
                    .map(this::setItemRequestDto)
                    .collect(Collectors.toList());
        }
        log.debug("");
        return requestRepository.findAll().stream()
                .map(RequestMapper::toRequestDto)
                .map(this::setItemRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto getById(long requesterId, long requestId) {
        if (!userRepository.existsById(requesterId)) {
            log.warn("Requester not exists");
            throw new NotFound("Requester not exists");
        }
        RequestDto requestDto = RequestMapper.toRequestDto(getItemRequest(requestId));
        return setItemRequestDto(requestDto);
    }

    @Override
    public void delete(long requestId) {
        getItemRequest(requestId);
        requestRepository.deleteById(requestId);
        log.debug("Request deleted");
    }

    private Request getItemRequest(long id) {
        return requestRepository.findById(id).orElseThrow(() -> {
            log.warn("Request not found");
            throw new NotFound("Request not found");
        });
    }

    private RequestDto setItemRequestDto(RequestDto requestDto) {
        List<ItemDto> itemsDto = itemRepository.findAllByRequestId(requestDto.getId()).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
        requestDto.setItems(itemsDto);
        return requestDto;
    }
}
