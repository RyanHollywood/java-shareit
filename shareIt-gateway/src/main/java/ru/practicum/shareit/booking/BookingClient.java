package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingItemRequestDto;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.exceptions.errors.BadRequestException;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";
    private static final String UNSUPPORTED_STATUS = "UNSUPPORTED_STATUS";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getBookings(long userId, String status, Integer from, Integer size) {
        BookingStatus bookingStatus = BookingStatus.statusFromString(status);
        if (bookingStatus == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unknown state: " + UNSUPPORTED_STATUS));
        }
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new BadRequestException("From and size parameters are negative or equal zero");
            }
            Map<String, Object> parameters = Map.of(
                    "state", bookingStatus.name(),
                    "from", from,
                    "size", size
            );
            return get("?state={state}&from={from}&size={size}", userId, parameters);
        }
        Map<String, Object> parameters = Map.of(
                "state", bookingStatus.name()
        );
        return get("?state={state}", userId, parameters);
    }


    public ResponseEntity<Object> bookItem(long userId, BookingItemRequestDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getBooking(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<?> updateBookingById(long userId, long bookingId, Boolean isApproved) {
        Map<String, Object> parameters = Map.of(
                "isApproved", isApproved
        );
        return patch("/" + bookingId + "?approved=" + isApproved, userId, parameters);
    }

    public ResponseEntity<?> getAllBookingsByOwner(long userId, Integer from, Integer size, String status) {
        BookingStatus bookingStatus = BookingStatus.statusFromString(status);
        if (bookingStatus == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unknown state: " + UNSUPPORTED_STATUS));
        }
        if (from != null && size != null) {
            if (from < 0 || size <= 0) {
                throw new BadRequestException("From and size parameters are negative or equal zero");
            }
            Map<String, Object> parameters = Map.of(
                    "state", bookingStatus.name(),
                    "from", from,
                    "size", size
            );
            return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
        }
        System.out.println(bookingStatus);
        Map<String, Object> parameters = Map.of(
                "state", bookingStatus.name()
        );
        return get("/owner?state={state}", userId, parameters);
    }
}
