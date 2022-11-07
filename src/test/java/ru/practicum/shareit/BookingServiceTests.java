package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.BookingServiceImpl;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.exception.InvalidItemException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class BookingServiceTests {
    UserRepository  userRepository = Mockito.mock(UserRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    BookingService bookingService = new BookingServiceImpl(userRepository, itemRepository, bookingRepository);

    @Test
    public void testCreateInvalidUser(){
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> {bookingService.create(bookingDto);});
    }

    @Test
    public void testCreateInvalidItem(){
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User("name", "email")));
        Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> {bookingService.create(bookingDto);});
    }

    @Test
    public void testCreateInvalidOwner(){
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User(1, "name", "email")));
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(new Item(1,
                new User(2, "test", "email"), "test", "test", true)));
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> {bookingService.create(bookingDto);});
    }

    @Test
    public void testCreateValidation(){
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2,"test", "email"), "test", "test", true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        BookingDto bookingDtoBefore = new BookingDto(1, 1, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now(), Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> {bookingService.create(bookingDtoBefore);});

        BookingDto bookingDtoBeforeTo = new BookingDto(1, 1, 1, LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(1), Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> {bookingService.create(bookingDtoBeforeTo);});

        BookingDto bookingDtoToLessFrom = new BookingDto(1, 1, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(2), Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> {bookingService.create(bookingDtoToLessFrom);});

        item.setAvailable(false);
        BookingDto bookingDtoUnavailable = new BookingDto(1, 1, 1, LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(4), Status.WAITING, "Test");
        Assertions.assertThrows(InvalidItemException.class, () -> {bookingService.create(bookingDtoUnavailable);});
    }

    @Test
    public void testCreateSuccess(){
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2,"test", "email"), "test", "test", true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        BookingDto bookingDto = new BookingDto(1, 1, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3), Status.WAITING, "Test");
        Booking booking = BookingMapper.fromDto(user, item, bookingDto);
        Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(booking);
        BookingDto created = bookingService.create(bookingDto);

        bookingDto.setBooker(UserMapper.toDto(user));
        bookingDto.setItem(ItemMapper.toDto(item));

        Assertions.assertEquals(bookingDto, created);
    }

    @Test
    public void testUpdateInvalidBooking(){
        Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> {bookingService.update(1, bookingDto);});
    }

    @Test
    public void testUpdateSuccess(){
        User user = new User(1, "name", "email");
        Item item = new Item(1, user, "test", "test", true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.WAITING,
                "Test");
        Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(booking));
        BookingDto updated = new BookingDto(Status.APPROVED, "Test updated");
        booking.setStatus(Status.APPROVED);
        booking.setReview("Test updated");
        Assertions.assertEquals(BookingMapper.toDto(booking), bookingService.update(1, updated));
    }
}
