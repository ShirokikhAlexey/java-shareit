package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.InvalidItemException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.CommentRepository;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.ItemRequestRepository;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ItemServiceTests {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
    ItemRequestRepository itemRequestRepository = Mockito.mock(ItemRequestRepository.class);

    ItemService itemService = new ItemServiceImpl(userRepository, itemRepository, bookingRepository,
            commentRepository, itemRequestRepository);

    @Test
    public void testCreateInvalidUser() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        ItemDto itemDto = new ItemDto(1, "Test", "Test", true);
        Assertions.assertThrows(NotFoundException.class, () -> itemService.create(itemDto, 1));
    }

    @Test
    public void testCreateInvalidItem() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User("name", "email")));

        ItemDto itemNoAvailable = new ItemDto("Test", "test", null, 1);
        Assertions.assertThrows(ValidationException.class, () -> itemService.create(itemNoAvailable, 1));

        ItemDto itemNoName = new ItemDto(null, "test", true, 1);
        Assertions.assertThrows(ValidationException.class, () -> itemService.create(itemNoName, 1));
        itemNoName.setName(" ");
        Assertions.assertThrows(ValidationException.class, () -> itemService.create(itemNoName, 1));

        ItemDto itemNoDescription = new ItemDto("Test", null, true, 1);
        Assertions.assertThrows(ValidationException.class, () -> itemService.create(itemNoDescription, 1));
        itemNoDescription.setName(" ");
        Assertions.assertThrows(ValidationException.class, () -> itemService.create(itemNoDescription, 1));
    }

    @Test
    public void testInvalidSuggestion() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User("name", "email")));
        Mockito.when(itemRequestRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ItemDto itemDto = new ItemDto("Test", "test", true, 1);
        Assertions.assertThrows(NotFoundException.class, () -> itemService.create(itemDto, 1));
    }

    @Test
    public void testCreateSuccess() {
        User user = new User(1,"name", "email");
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRequestRepository.findById(Mockito.anyInt()))
                .thenReturn(
                        Optional.of(new ItemRequest(1, new User(2, "test", "test"),
                                "test", Status.OPEN, LocalDateTime.now(), new ArrayList<>())));

        ItemDto itemDto = new ItemDto("Test", "test", true, 1);

        Item item = ItemMapper.fromDto(user, itemDto);

        Mockito.when(itemRepository.save(Mockito.any())).thenReturn(item);
        Assertions.assertEquals(itemDto, itemService.create(itemDto, 1));
    }

    @Test
    public void testUpdateInvalidItem() {
        Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ItemDto itemDto = new ItemDto(1,"Test", "test", true, null);
        Assertions.assertThrows(NotFoundException.class, () -> itemService.update(1, itemDto, 1));
    }

    @Test
    public void testUpdateInvalidOwner() {
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(new Item(
                new User(1, "test", "test"),"test", "test", true)));
        ItemDto itemDto = new ItemDto("Test", "test", true, null);
        Assertions.assertThrows(NotFoundException.class, () -> itemService.update(1, itemDto, 2));
    }

    @Test
    public void testUpdateSuccess() {
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(new Item(1,
                new User(1, "test", "test"),"test", "test", true)));
        ItemDto itemDto = new ItemDto(1, "TestUpdate", "TestUpdate", false);
        Assertions.assertEquals(itemDto, itemService.update(1, itemDto, 1));
    }

    @Test
    public void testAddCommentInvalid() {
        CommentDto emptyReview = new CommentDto(" ");
        Assertions.assertThrows(ValidationException.class, () -> itemService.addComment(emptyReview));
    }

    @Test
    public void testAddCommentNotFound() {
        User user = new User(1, "test", "test");
        Item item = new Item(1, user, "test", "test", true);

        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        CommentDto commentDto = new CommentDto(1, 1, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> itemService.addComment(commentDto));
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));

        Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> itemService.addComment(commentDto));
        Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(item));

        Mockito.when(bookingRepository.getUserItemBooking(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<>());
        Assertions.assertThrows(InvalidItemException.class, () -> itemService.addComment(commentDto));
    }

    @Test
    public void testAddCommentSuccess() {
        User user = new User(1, "test", "test");
        Item item = new Item(1, user, "test", "test", true);
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(item));
        Mockito.when(bookingRepository.getUserItemBooking(Mockito.anyInt(), Mockito.anyInt())).thenReturn(List.of(new Booking()));
        CommentDto commentDto = new CommentDto(1, 1, 1, "test", "test", LocalDateTime.now());

        Comment comment = CommentMapper.fromDto(user, item, commentDto);

        Mockito.when(commentRepository.save(Mockito.any())).thenReturn(comment);
        CommentDto created = itemService.addComment(commentDto);


        Assertions.assertEquals(commentDto.getReview(), created.getReview());
    }
}
