package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestServiceImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.*;

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
        itemRequestDto.setRequesterId(requestorId);
        itemRequestDto.setCreated(LocalDateTime.now());
        return itemRequestService.create(itemRequestDto);

    }

    @GetMapping
    public List<ItemRequestDto> getByRequester(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        return itemRequestService.getByRequester(requesterId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAll(@RequestHeader("X-Sharer-User-Id") long requesterId ,
                                       @RequestParam(value = "from", required = false) Optional<Integer> from,
                                       @RequestParam(value = "size", required = false) Optional<Integer> size) {

        return itemRequestService.getAll(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto get(@RequestHeader("X-Sharer-User-Id") long requestorId,
                              @PathVariable long requestId) {

        return itemRequestService.getById(requestId);
    }

    @DeleteMapping("/{requestId}")
    public void delete(@PathVariable long requestId) {
        itemRequestService.delete(requestId);
    }
}
