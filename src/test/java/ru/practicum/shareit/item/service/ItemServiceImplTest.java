package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.comment.CommentMapper;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.storage.CommentRepository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private User user;
    private ItemDto itemDto;
    private Item item;

    @BeforeEach
    void setUp() {
        user = new User(2, "User", "Email@email.com");
        itemDto = new ItemDto(1, "Item", "ItemDescription", true, 1, Optional.of(1L));
        item = new Item(1, "Item", "ItemDescription", true, 1, 1);

    }

    @Test
    void create() {
        checkUser();
        when(itemRepository.save(any()))
                .thenReturn(item);
        assertEquals(ItemMapper.toItemDto(item), itemService.create(itemDto, 1));
    }

    @Test
    void update() {
    }

    @Test
    void getById() {
        Booking nextBooking = new Booking(LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusMinutes(10),
                item, user, BookingStatus.WAITING);
        Booking lastBooking = new Booking(LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now().plusMinutes(5), item, user, BookingStatus.WAITING);
        Comment comment = new Comment(1, "Comment", item, user,  LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())));
        checkItem();
        when(bookingRepository.findAllByItemIdInOrderByStartDesc(any()))
                .thenReturn(List.of(nextBooking, lastBooking));
        when(commentRepository.findAll())
                .thenReturn(List.of(comment));
        ItemDto itemToCheck = ItemMapper.toItemDto(item);
        itemToCheck.setNextBooking(BookingMapper.toBookingItemDto(nextBooking));
        itemToCheck.setLastBooking(BookingMapper.toBookingItemDto(lastBooking));
        itemToCheck.setComments(List.of(CommentMapper.toCommentDto(comment)));
        assertEquals(itemToCheck, itemService.getById(1, 1));
    }

    @Test
    void getAll() {
        Booking nextBooking = new Booking(LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusMinutes(10),
                item, user, BookingStatus.WAITING);
        Booking lastBooking = new Booking(LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now().plusMinutes(5), item, user, BookingStatus.WAITING);
        Comment comment = new Comment(1, "Comment", item, user, LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())));
        getById();
        when(itemRepository.findAllByOwnerId(anyLong()))
                .thenReturn(List.of(item));
        ItemDto itemToCheck = ItemMapper.toItemDto(item);
        itemToCheck.setNextBooking(BookingMapper.toBookingItemDto(nextBooking));
        itemToCheck.setLastBooking(BookingMapper.toBookingItemDto(lastBooking));
        itemToCheck.setComments(List.of(CommentMapper.toCommentDto(comment)));
        assertEquals(1, itemService.getAll(1).size());
        assertEquals(itemToCheck, itemService.getAll(1).get(0));
    }

    @Test
    void search() {
        when(itemRepository.search(anyString()))
                .thenReturn(List.of(item));
        assertEquals(ItemMapper.toItemDto(item), itemService.search("item", 1).get(0));
    }

    @Test
    void delete() {
        checkItem();
        itemService.delete(1);
        Mockito.verify(itemRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    void addComment() {
    }

    private void checkItem() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
    }

    private void checkUser() {
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
    }
}