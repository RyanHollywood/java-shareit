package ru.practicum.shareit.comment.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentDtoTest {

    private CommentDto commentDto;
    private final long id = 1;
    private final String text = "Text";
    private final Item item = null;
    private final String authorName = "AuthorName";
    private LocalDateTime created = LocalDateTime.now();

    @BeforeEach
    void reload() {
        commentDto = new CommentDto(id, text, item, authorName, created);
    }

    @Test
    void getId() {
        assertEquals(id, commentDto.getId());
    }

    @Test
    void getText() {
        assertEquals(text, commentDto.getText());
    }

    @Test
    void getItem() {
        assertEquals(item, commentDto.getItem());
    }

    @Test
    void getAuthor() {
        assertEquals(authorName, commentDto.getAuthorName());
    }

    @Test
    void getCreated() {
        assertEquals(created, commentDto.getCreated());
    }

    @Test
    void setId() {
        long newId = 2;
        commentDto.setId(newId);
        assertEquals(newId, commentDto.getId());
    }

    @Test
    void setText() {
        String newText = "New text";
        commentDto.setText(newText);
        assertEquals(newText, commentDto.getText());
    }

    @Test
    void setItem() {
        Item newItem = Item.builder()
                .id(1)
                .name("name")
                .description("description")
                .available(true)
                .ownerId(1)
                .build();
        commentDto.setItem(newItem);
        assertEquals(newItem, commentDto.getItem());
    }

    @Test
    void setAuthor() {
        String newAuthorName = "NewAuthorName";
        commentDto.setAuthorName(newAuthorName);
        assertEquals(newAuthorName, commentDto.getAuthorName());
    }

    @Test
    void setCreated() {
        LocalDateTime newCreated = LocalDateTime.now().plusHours(1);
        commentDto.setCreated(newCreated);
        assertEquals(newCreated, commentDto.getCreated());
    }

    @Test
    void testEquals() {
        CommentDto equalCommentDto = new CommentDto(id, text, item, authorName, created);
        assertEquals(equalCommentDto, commentDto);
    }
}