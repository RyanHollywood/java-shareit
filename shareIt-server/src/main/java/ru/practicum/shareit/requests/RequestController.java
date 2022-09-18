package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.RequestDto;
import ru.practicum.shareit.requests.service.RequestServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestServiceImpl itemRequestService;

    @Autowired
    public RequestController(RequestServiceImpl itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public RequestDto create(@RequestHeader("X-Sharer-User-Id") long requesterId,
                             @RequestBody RequestDto requestDto) {
        requestDto.setRequesterId(requesterId);
        requestDto.setCreated(LocalDateTime.now().withNano(0));
        return itemRequestService.create(requestDto);

    }

    @GetMapping
    public List<RequestDto> getByRequester(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        return itemRequestService.getByRequester(requesterId);
    }

    @GetMapping("/all")
    public List<RequestDto> getAll(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                   @RequestParam(value = "from", required = false) Integer from,
                                   @RequestParam(value = "size", required = false) Integer size) {

        return itemRequestService.getAll(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public RequestDto get(@RequestHeader("X-Sharer-User-Id") long requesterId,
                          @PathVariable long requestId) {
        return itemRequestService.getById(requesterId, requestId);
    }

    @DeleteMapping("/{requestId}")
    public void delete(@PathVariable long requestId) {
        itemRequestService.delete(requestId);
    }
}
