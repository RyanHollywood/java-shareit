package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.storage.CommentRepository;
import ru.practicum.shareit.exceptions.errors.BadRequest;
import ru.practicum.shareit.exceptions.errors.NotFound;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, BookingService bookingService,
                           BookingRepository bookingRepository, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ItemDto create(ItemDto itemDto, long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            log.warn("Owner not exists");
            throw new NotFound("Owner not exists");
        }
        log.debug("Item created");
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(itemDto)));
    }

    @Override
    public ItemDto update(long id, long ownerId, JsonNode object) {
        Item itemToUpdate = getItem(id);
        if (ownerId != itemToUpdate.getOwnerId()) {
            log.warn("User is not owner of item");
            throw new NotFound("User is not owner of item");
        }
        if (object.has("name")) {
            itemToUpdate.setName(object.get("name").textValue());
            log.debug("Item name updated");
        }
        if (object.has("description")) {
            itemToUpdate.setDescription(object.get("description").textValue());
            log.debug("Item description updated");
        }
        if (object.has("available")) {
            itemToUpdate.setAvailable(object.get("available").asBoolean());
            log.debug("Item status updated");
        }
        return ItemMapper.toItemDto(itemRepository.save(itemToUpdate));
    }

    @Override
    public ItemDto getById(long id, long ownerId) {
        Item itemToGet = getItem(id);
        ItemDto itemDtoToGet = ItemMapper.toItemDto(itemToGet);
        List<Booking> bookings = bookingRepository.findAllByItemIdInOrderByStartDesc(List.of(id));
        if (ownerId == itemDtoToGet.getOwnerId()) {
            if (bookings.size() > 0) {
                itemDtoToGet.setNextBooking(BookingMapper.toBookingItemDto(bookings.get(0)));
            }
            if (bookings.size() > 1) {
                itemDtoToGet.setLastBooking(BookingMapper.toBookingItemDto(bookings.get(1)));
            }
        }
        itemDtoToGet.setComments(commentRepository.findAll().stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList()));
        log.debug("Item found");
        return itemDtoToGet;
    }

    @Override
    public List<ItemDto> getAll(long ownerId) {
        log.debug("Items by owner found");
        return itemRepository.findAllByOwnerId(ownerId).stream()
                .map(ItemMapper::toItemDto)
                .map(itemDto -> getById(itemDto.getId(), itemDto.getOwnerId()))
                .sorted((Comparator.comparingLong(ItemDto::getId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text, long ownerId) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        log.debug("Items found by search");
        return itemRepository.search(text.toLowerCase()).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        getItem(id);
        itemRepository.deleteById(id);
        log.debug("Item deleted");
    }

    @Override
    public CommentDto addComment(long userId, long itemId, CommentDto commentDto) {
        List<BookingDto> bookings = bookingService.getAll(userId, "PAST", null, null);
        List<Long> bookers = bookings.stream()
                .map(BookingDto::getBooker)
                .map(User::getId)
                .collect(Collectors.toList());
        if (bookers.size() == 0) {
            throw new BadRequest("User have not booked the item");
        }
        Comment commentToAdd = CommentMapper.toComment(commentDto);
        commentToAdd.setItem(itemRepository.findById(itemId).orElseThrow(() -> {
            log.warn("Item not found");
            throw new NotFound("Item not found");
        }));
        commentToAdd.setAuthor(userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Author not found");
            throw new NotFound("Author not found");
        }));
        commentToAdd.setCreated(LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())));
        log.debug("Comment created");
        return CommentMapper.toCommentDto(commentRepository.save(commentToAdd));
    }

    private Item getItem(long id) {
        return itemRepository.findById(id).orElseThrow(() -> {
            log.warn("Item not found");
            return new NotFound("Item not found");
        });
    }
}
