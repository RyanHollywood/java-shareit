package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.service.ItemRequestServiceImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
        itemRequestDto.setCreated(LocalDateTime.now());
        return itemRequestService.create(itemRequestDto, requestorId);

    }

    @GetMapping
    public List<ItemRequestDto> getByRequestor(@RequestHeader("X-Sharer-User-Id") long requestorId) {
        return itemRequestService.getByRequestor(requestorId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAll(@RequestHeader("X-Sharer-User-Id") long requestorId ,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                       @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return itemRequestService.getAll(requestorId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto get(@RequestHeader("X-Sharer-User-Id") long requestorId,
                              @PathVariable long requestId) {
        return itemRequestService.getById(requestorId, requestId);
    }

    @DeleteMapping("/{requestId}")
    public void delete(@PathVariable long requestId) {
        itemRequestService.delete(requestId);
    }
}
