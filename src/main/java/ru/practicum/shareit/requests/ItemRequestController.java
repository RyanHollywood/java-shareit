package ru.practicum.shareit.requests;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    @PostMapping
    public ItemRequestDto create() {
        return null;
    }

    @PatchMapping("/{requestId}")
    public ItemRequestDto update() {
        return null;
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto get() {
        return null;
    }

    @GetMapping
    public List<ItemRequestDto> getAll() {
        return null;
    }

    @DeleteMapping("/{requestId}")
    public void delete() {

    }
}
