package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/requests")
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<?> addRequest(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                        @Valid @RequestBody RequestDto requestDto) {
        return requestClient.addRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<?> getRequest(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return requestClient.getRequest(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRequests(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                            @RequestParam(value = "from", required = false) Integer from,
                                            @RequestParam(value = "size", required = false) Integer size) {
        return requestClient.getAllRequests(userId, from, size);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<?> getRequestById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                            @PathVariable(name = "requestId") long requestId) {
        return requestClient.getRequestById(userId, requestId);
    }
}
