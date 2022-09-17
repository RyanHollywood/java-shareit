package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/items")
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<?> addItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                     @RequestBody ItemDto itemDto) {
        if (itemDto.getAvailable() == null || itemDto.getName().isBlank() || itemDto.getDescription() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<?> updateItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                        @PathVariable int itemId,
                                        @RequestBody JsonNode object) {
        return itemClient.updateItem(userId, itemId, object);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                     @PathVariable int itemId) {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<?> getItemsByUser(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                            @RequestParam(value = "from", required = false) Integer from,
                                            @RequestParam(value = "size", required = false) Integer size) {
        return itemClient.getItemsByUser(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findItemByName(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                            @RequestParam(value = "text", required = false) String text,
                                            @RequestParam(value = "from", required = false) Integer from,
                                            @RequestParam(value = "size", required = false) Integer size) {
        return itemClient.findItemByName(text, userId, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<?> addComment(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                        @PathVariable long itemId,
                                        @RequestBody CommentDto comment) {
        return itemClient.addComment(userId, itemId, comment);
    }
}
