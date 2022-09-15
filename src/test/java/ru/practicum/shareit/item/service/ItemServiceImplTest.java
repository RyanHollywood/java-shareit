package ru.practicum.shareit.item.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    private final ObjectMapper mapper = new ObjectMapper();
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
    private BookingDto bookingDto;
    private Comment comment;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        user = new User(2, "User", "Email@email.com");
        itemDto = new ItemDto(1, "Item", "ItemDescription", true, 1, 1L);
        item = new Item(1, "Item", "ItemDescription", true, 1, 1);
        bookingDto = new BookingDto(1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), item, user, BookingStatus.WAITING);
        comment = new Comment(1, "Text", item, user, LocalDateTime.now());
        commentDto = CommentMapper.toCommentDto(comment);
    }

    @Test
    void create() {
        checkUserOk();
        when(itemRepository.save(any()))
                .thenReturn(item);
        assertEquals(ItemMapper.toItemDto(item), itemService.create(itemDto, 1));

        checkUserNotExist();
        try {
            itemService.create(itemDto, 1);
        } catch (NotFound exception) {
            assertEquals("Owner not exists", exception.getMessage());
        }
    }

    @Test
    void update() throws JsonProcessingException {
        checkItemOk();
        when(itemRepository.save(any()))
                .thenReturn(item);
        item.setName("NewName");
        item.setDescription("NewDescription");
        item.setAvailable(false);
        JsonNode updateParameters = mapper.readTree("{\"name\":\"NewName\", \"description\": \"NewDescription\", \"available\":false}");
        assertEquals(ItemMapper.toItemDto(item), itemService.update(1, 1, updateParameters));

        try {
            itemService.update(1, 2, updateParameters);
        } catch (NotFound exception) {
            assertEquals("User is not owner of item", exception.getMessage());
        }
    }

    @Test
    void getById() {
        Booking nextBooking = new Booking(LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusMinutes(10),
                item, user, BookingStatus.WAITING);
        Booking lastBooking = new Booking(LocalDateTime.now().plusMinutes(5),
                LocalDateTime.now().plusMinutes(5), item, user, BookingStatus.WAITING);
        Comment comment = new Comment(1, "Comment", item, user, LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())));
        checkItemOk();
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
    void getByIdWrongId() {
        try {
            itemService.getById(2, 1);
        } catch (NotFound exception) {
            assertEquals("Item not found", exception.getMessage());
        }
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
    void emptySearch() {
        assertEquals(0, itemService.search("", 1).size());
    }

    @Test
    void delete() {
        checkItemOk();
        itemService.delete(1);
        Mockito.verify(itemRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    void addComment() {
        checkItemOk();
        checkForBookingOk();
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(commentRepository.save(any()))
                .thenReturn(comment);
        assertEquals(commentDto, itemService.addComment(1, 1, commentDto));

        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(List.of());
        try {
            itemService.addComment(1, 1, commentDto);
        } catch (BadRequest exception) {
            assertEquals("User have not booked the item", exception.getMessage());
        }
    }

    @Test
    void addCommentWithNoBooking() {
        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(List.of());
        try {
            itemService.addComment(1, 1, commentDto);
        } catch (BadRequest exception) {
            assertEquals("User have not booked the item", exception.getMessage());
        }
    }

    @Test
    void addCommentWithNoItem() {
        checkItemNotExist();
        checkForBookingOk();
        try {
            itemService.addComment(1, 1, commentDto);
        } catch (NotFound exception) {
            assertEquals("Item not found", exception.getMessage());
        }
    }

    @Test
    void addCommentWithNoUser() {
        checkItemOk();
        checkForBookingOk();
        try {
            itemService.addComment(1, 1, commentDto);
        } catch (NotFound exception) {
            assertEquals("Author not found", exception.getMessage());
        }
    }

    private void checkItemOk() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(item));
    }

    private void checkItemNotExist() {
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
    }

    private void checkUserOk() {
        when(userRepository.existsById(anyLong()))
                .thenReturn(true);
    }

    private void checkUserNotExist() {
        when(userRepository.existsById(anyLong()))
                .thenReturn(false);
    }

    private void checkForBookingOk() {
        when(bookingService.getAll(anyLong(), anyString(), any(), any()))
                .thenReturn(List.of(bookingDto));
    }
}