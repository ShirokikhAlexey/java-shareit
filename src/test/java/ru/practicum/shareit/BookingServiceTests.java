package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BookingServiceTests {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    BookingService bookingService = new BookingServiceImpl(userRepository, itemRepository, bookingRepository);

    @Test
    public void testCreateInvalidUser() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.create(bookingDto));
    }

    @Test
    public void testCreateInvalidItem() {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User("name", "email")));
        Mockito.when(itemRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.create(bookingDto));
    }

    @Test
    public void testCreateInvalidOwner() {
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test", true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        BookingDto bookingDto = new BookingDto(1, 2, 1, LocalDateTime.now(),
                LocalDateTime.now(), Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.create(bookingDto));
    }

    @Test
    public void testCreateInvalidOwnerAnother() {
        User user = new User(1, "name", "email");
        Item item = new Item(1, user, "test", "test", true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        BookingDto bookingDto = new BookingDto(1, 2, 1, LocalDateTime.now(),
                LocalDateTime.now(), Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.create(bookingDto));
    }

    @Test
    public void testCreateValidation() {
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test", true);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(1)).thenReturn(Optional.of(item));

        BookingDto bookingDtoBefore = new BookingDto(1, 1, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now(), Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> bookingService.create(bookingDtoBefore));

        BookingDto bookingDtoBeforeTo = new BookingDto(1, 1, 1, LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(1), Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> bookingService.create(bookingDtoBeforeTo));

        BookingDto bookingDtoToLessFrom = new BookingDto(1, 1, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(2), Status.WAITING, "Test");
        Assertions.assertThrows(ValidationException.class, () -> bookingService.create(bookingDtoToLessFrom));

        item.setAvailable(false);
        BookingDto bookingDtoUnavailable = new BookingDto(1, 1, 1, LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(4), Status.WAITING, "Test");
        Assertions.assertThrows(InvalidItemException.class, () ->
                bookingService.create(bookingDtoUnavailable));
    }

    @Test
    public void testCreateSuccess() {
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test", true);
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
    public void testUpdateInvalidBooking() {
        Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        BookingDto bookingDto = new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(),
                Status.WAITING, "Test");
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.update(1, bookingDto));
    }

    @Test
    public void testUpdateSuccess() {
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

    @Test
    public void testGetByIdNotFound() {
        Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.get(1));
    }

    @Test
    public void testGetByIdSuccess() {
        User user = new User(1, "name", "email");
        Item item = new Item(1, user, "test", "test", true);
        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.WAITING,
                "Test");
        Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(booking));
        Assertions.assertEquals(BookingMapper.toDto(booking), bookingService.get(1));
    }

    @Test
    public void testGetStatusListNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.getStatusList(1, "ALL", 1, 1));
    }

    @Test
    public void testGetStatusListPast() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.PAST,
                "Test");
        Mockito.when(bookingRepository.getPast(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getStatusList(user.getId(), "PAST", 1, 1));
    }

    @Test
    public void testGetStatusCurrent() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.CURRENT,
                "Test");
        Mockito.when(bookingRepository.getCurrent(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getStatusList(user.getId(), "CURRENT", 1, 1));
    }

    @Test
    public void testGetStatusFuture() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.FUTURE,
                "Test");
        Mockito.when(bookingRepository.getFuture(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getStatusList(user.getId(), "FUTURE", 1, 1));
    }

    @Test
    public void testGetStatusWaiting() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.WAITING,
                "Test");
        Mockito.when(bookingRepository.getByStatus(Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getStatusList(user.getId(), "WAITING", 1, 1));
        Assertions.assertEquals(checkData, bookingService.getStatusList(user.getId(), "ALL", 1, 1));
    }

    @Test
    public void testGetStatusInvalid() {
        Assertions.assertThrows(ValidationException.class, () -> bookingService.getStatusList(1, "TEST", 1, 1));
    }

    @Test
    public void testGetUserItemsBookingsNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> bookingService.getUserItemsBookings(1, "ALL", 1, 1));
    }

    @Test
    public void testGetUserItemsBookingsPast() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.PAST,
                "Test");
        Mockito.when(bookingRepository.getOwnerPast(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getUserItemsBookings(2, "PAST", 1, 1));
    }

    @Test
    public void testGetUserItemsBookingsCurrent() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.CURRENT,
                "Test");
        Mockito.when(bookingRepository.getOwnerCurrent(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getUserItemsBookings(2, "CURRENT", 1, 1));
    }

    @Test
    public void testGetUserItemsBookingsFuture() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.FUTURE,
                "Test");
        Mockito.when(bookingRepository.getOwnerFuture(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getUserItemsBookings(2, "FUTURE", 1, 1));
    }

    @Test
    public void testGetUserItemsBookingsWaiting() {
        List<BookingDto> checkData = new ArrayList<>();
        User user = new User(1, "name", "email");
        Item item = new Item(1, new User(2, "test", "email"), "test", "test",
                true);

        Booking booking = new Booking(1, item, user, LocalDateTime.now(), LocalDateTime.now(), Status.WAITING,
                "Test");
        Mockito.when(bookingRepository.getUserItemsBookings(Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(List.of(booking));

        checkData.add(BookingMapper.toDto(booking));

        Assertions.assertEquals(checkData, bookingService.getUserItemsBookings(2, "WAITING", 1, 1));
        Assertions.assertEquals(checkData, bookingService.getUserItemsBookings(2, "ALL", 1, 1));
    }

    @Test
    public void testGetUserItemsBookingsInvalid() {
        Assertions.assertThrows(ValidationException.class, () -> bookingService.getUserItemsBookings(1, "PAST", 0, 1));
        Assertions.assertThrows(ValidationException.class, () -> bookingService.getUserItemsBookings(1, "TEST", 1, 1));
    }
}
