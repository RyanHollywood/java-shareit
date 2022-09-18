package ru.practicum.shareit.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

    private Comment comment;
    private CommentDto commentDto;
    private final long id = 1;
    private final String text = "Text";
    private final Item item = null;
    private final User author = new User(1, "AuthorName", "Email@email.com");
    private final String authorName = "AuthorName";
    private LocalDateTime created = LocalDateTime.now();

    @BeforeEach
    void reload() {
        comment = new Comment(id, text, item, author, created);
        commentDto = new CommentDto(id, text, item, authorName, created);
    }

    @Test
    void toCommentDto() {
        assertEquals(commentDto, CommentMapper.toCommentDto(comment));
    }

    @Test
    void toComment() {
        assertEquals(comment.getText(), CommentMapper.toComment(commentDto).getText());
    }
}