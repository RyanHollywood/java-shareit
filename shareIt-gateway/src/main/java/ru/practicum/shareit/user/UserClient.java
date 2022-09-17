package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<?> createUser(UserDto userDto) {
        return post("", userDto);
    }

    public ResponseEntity<?> getAllUsers() {
        return get("");
    }

    public ResponseEntity<?> getUser(long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<?> updateUser(Long userId, JsonNode object) {
        return patch("/" + userId, object);
    }

    public ResponseEntity<?> deleteUser(long userId) {
        return delete("/" + userId);
    }
}
