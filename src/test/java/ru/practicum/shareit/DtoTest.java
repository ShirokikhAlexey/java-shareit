package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemRequestDtoMapper;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class DtoTest {
    @Test
    public void testToDto() {
        User user = new User(1, "test", "test");
        Item item = new Item(1, user, "test", "test", true);
        ItemRequest requestUser = new ItemRequest(1, user, "test", Status.OPEN, LocalDateTime.now(), List.of(item));
        ItemRequestDtoMapper.toDto(requestUser);
        new ItemRequestDto("test", UserMapper.toDto(user), List.of(ItemMapper.toDto(item)), Status.OPEN, LocalDateTime.now());
        new Booking(item, user, LocalDateTime.now(), LocalDateTime.now(), "APPROVED", "test");
        new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(), "APPROVED","test");
        new BookingDto(1, 1, 1, LocalDateTime.now(), LocalDateTime.now(), "APPROVED", "test", UserMapper.toDto(user), ItemMapper.toDto(item));
    }

    @Test
    public void errorResponseTest() {
        ErrorResponse error = new ErrorResponse("test");
        Assertions.assertEquals(error.getError(), "test");
    }

    @Test
    public void errorHandlerTest() {
        ErrorHandler handler = new ErrorHandler();

        Assertions.assertEquals(handler.handleValidationException(new ValidationException("test")).getError(), "test");
        Assertions.assertNull(handler.handleNotFoundException(new NotFoundException()).getError());
        Assertions.assertNull(handler.handleInvalidItem(new InvalidItemException()).getError());
        Assertions.assertNull(handler.handleInvalidUserParameters(new InvalidUserParameters()).getError());
        Assertions.assertNull(handler.handleException(new NullPointerException()).getError());
    }

    @Test
    public void itemMapperTest() {
        User user = new User(1, "test", "test");
        Item item = new Item(1, user, "test", "test", true);
        ItemDto dto = new ItemDto();
        ItemMapper.updateFromDto(item, dto);
    }

    @Test
    public void userMapperTest() {
        User user = new User(1, "test", "test");
        UserDto dto = new UserDto();
        UserMapper.updateFromDto(user, dto);
    }
}
