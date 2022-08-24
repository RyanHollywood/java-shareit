package ru.practicum.shareit.requests.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private final UserRepository userRepository;

    public ItemRequestServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ItemRequestDto create(ItemRequestDto itemDto, long requestor) {
        return null;
    }

    @Override
    public ItemRequestDto getById(long id, long requestor) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getAll(long requestor) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
