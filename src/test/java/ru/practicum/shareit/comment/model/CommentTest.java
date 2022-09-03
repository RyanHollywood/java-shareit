package ru.practicum.shareit.comment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;
    private final long id = 1;
    private final String text = "Text";
    private final Item item = null;
    private final User author = null;
    private LocalDateTime created = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        comment = new Comment(id, text, item, author, created);
    }

    @Test
    void getId() {
        assertEquals(id, comment.getId());
    }

    @Test
    void getText() {
        assertEquals(text, comment.getText());
    }

    @Test
    void getItem() {
        assertEquals(item, comment.getItem());
    }

    @Test
    void getAuthor() {
        assertEquals(author, comment.getAuthor());
    }

    @Test
    void getCreated() {
        assertEquals(created, comment.getCreated());
    }

    @Test
    void setId() {
        long newId = 2;
        comment.setId(newId);
        assertEquals(newId, comment.getId());
    }

    @Test
    void setText() {
        String newText = "New text";
        comment.setText(newText);
        assertEquals(newText, comment.getText());
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
        comment.setItem(newItem);
        assertEquals(newItem, comment.getItem());
    }

    @Test
    void setAuthor() {
        User newAuthor = User.builder()
                .id(1)
                .name("name")
                .email("email")
                .build();
        comment.setAuthor(newAuthor);
        assertEquals(newAuthor, comment.getAuthor());
    }

    @Test
    void setCreated() {
        LocalDateTime newCreated = LocalDateTime.now().plusHours(1);
        comment.setCreated(newCreated);
        assertEquals(newCreated, comment.getCreated());
    }

    @Test
    void testEquals() {
        Comment equalComment = new Comment(id, text, item, author, created);
        assertEquals(equalComment, comment);
    }
}