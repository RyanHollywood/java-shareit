package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exceptions.errors.BadRequestException;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<?> addItem(long userId, ItemDto itemDto) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<?> updateItem(long userId, long itemId, JsonNode object) {
        return patch("/" + itemId, userId, object);
    }

    public ResponseEntity<?> getItem(long itemId, long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<?> getItemsByUser(long userId, Integer from, Integer size) {
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new BadRequestException("From and size parameters are negative or equal zero");
            }
            Map<String, Object> parameters = Map.of(
                    "from", from,
                    "size", size
            );
            return get("", userId, parameters);
        }
        return get("", userId);
    }

    public ResponseEntity<?> findItemByName(String text, long userId, Integer from, Integer size) {
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new BadRequestException("From and size parameters are negative or equal zero");
            }
            Map<String, Object> parameters = Map.of(
                    "from", from,
                    "size", size
            );
            return get("/search?text=" + text, userId, parameters);
        }
        return get("/search?text=" + text, userId);
    }

    public ResponseEntity<?> addComment(long userId, long itemId, CommentDto comment) {
        return post("/" + itemId + "/comment", userId, comment);
    }
}
