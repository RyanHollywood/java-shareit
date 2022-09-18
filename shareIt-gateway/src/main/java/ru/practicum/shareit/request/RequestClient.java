package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exceptions.errors.BadRequestException;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.Map;

@Service
public class RequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<?> addRequest(long userId, RequestDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<?> getRequest(long userId) {
        return get("", userId);
    }


    public ResponseEntity<?> getAllRequests(long userId, Integer from, Integer size) {
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new BadRequestException("From and size parameters are negative or equal zero");
            }
            Map<String, Object> parameters = Map.of(
                    "from", from,
                    "size", size
            );
            return get("/all", userId, parameters);
        }
        return get("/all", userId);
    }

    public ResponseEntity<?> getRequestById(long userId, long requestId) {
        return get("/" + requestId, userId);
    }
}
