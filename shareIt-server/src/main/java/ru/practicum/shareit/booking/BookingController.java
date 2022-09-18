package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto create(@RequestHeader(value = "X-Sharer-User-Id") long userId, @RequestBody BookingRequestDto requestDto) {
        requestDto.setBookerId(userId);
        return bookingService.create(requestDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto update(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long bookingId,
                             @RequestParam(value = "approved") boolean parameter) {
        return bookingService.update(userId, bookingId, parameter);
    }

    @GetMapping("/{bookingId}")
    public BookingDto get(@RequestHeader(value = "X-Sharer-User-Id") long userId, @PathVariable long bookingId) {
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                   @RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
                                   @RequestParam(value = "from", required = false) Integer from,
                                   @RequestParam(value = "size", required = false) Integer size) {
        return bookingService.getAll(userId, state, from, size);
    }


    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable long bookingId) {
        bookingService.delete(bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllByOwner(@RequestHeader(value = "X-Sharer-User-Id") long ownerId,
                                          @RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
                                          @RequestParam(value = "from", required = false) Integer from,
                                          @RequestParam(value = "size", required = false) Integer size) {
        return bookingService.getAllByOwner(ownerId, state, from, size);
    }

}
