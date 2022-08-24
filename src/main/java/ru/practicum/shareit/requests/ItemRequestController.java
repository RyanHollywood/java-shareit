package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestServiceImpl itemRequestService;

    @Autowired
    public ItemRequestController(ItemRequestServiceImpl itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") long requestorId,
                                 @Valid @RequestBody ItemRequestDto itemRequestDto) {
        itemRequestDto.setRequestor(requestorId);
        return itemRequestService.create(itemRequestDto, requestorId);

    }

    @GetMapping("/{requestId}")
    public ItemRequestDto get(@RequestHeader("X-Sharer-User-Id") long requestorId,
                              @PathVariable long id) {
        return itemRequestService.getById(id, requestorId);
    }

    @GetMapping
    public List<ItemRequestDto> getAll(@RequestHeader("X-Sharer-User-Id") long requestorId) {
        return itemRequestService.getAll(requestorId);
    }

    @DeleteMapping("/{requestId}")
    public void delete() {

    }
}
