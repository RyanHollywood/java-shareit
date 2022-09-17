package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingItemRequestDto;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @GetMapping
    public ResponseEntity<Object> getBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                              @RequestParam(value = "state", defaultValue = "ALL", required = false) String status,
                                              @RequestParam(value = "from", required = false) Integer from,
                                              @RequestParam(value = "size", required = false) Integer size) {
        return bookingClient.getBookings(userId, status, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestBody @Valid BookingItemRequestDto requestDto) {
        return bookingClient.bookItem(userId, requestDto);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable long bookingId) {
        return bookingClient.getBooking(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @PathVariable long bookingId,
                                           @RequestParam(value = "approved") Boolean isApproved) {
        return bookingClient.updateBookingById(userId, bookingId, isApproved);
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                   @RequestParam(value = "state", defaultValue = "ALL", required = false) String status,
                                                   @RequestParam(value = "from", required = false) Integer from,
                                                   @RequestParam(value = "size", required = false) Integer size) {
        return bookingClient.getAllBookingsByOwner(userId, from, size, status);
    }
}
