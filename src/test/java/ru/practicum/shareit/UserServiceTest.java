package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void testCreateInvalidData() {
        UserDto invalidUser = new UserDto(1, null, "test@test.test", new ArrayList<>());
        Assertions.assertThrows(InvalidUserParameters.class, () -> userService.create(invalidUser));

        invalidUser.setName(" ");
        Assertions.assertThrows(InvalidUserParameters.class, () -> userService.create(invalidUser));

        invalidUser.setName("Test");
        invalidUser.setEmail(null);
        Assertions.assertThrows(InvalidUserParameters.class, () -> userService.create(invalidUser));

        invalidUser.setEmail(" ");
        Assertions.assertThrows(InvalidUserParameters.class, () -> userService.create(invalidUser));

        invalidUser.setEmail("test");
        Assertions.assertThrows(ValidationException.class, () -> userService.create(invalidUser));
    }

    @Test
    public void testCreateSuccess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User(1, "test", "test@test.test"));
        UserDto userDto = new UserDto(1, "test", "test@test.test", new ArrayList<>());
        UserDto created = userService.create(userDto);
        Assertions.assertEquals(userDto, created);
    }

    @Test
    public void testUpdateNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        UserDto userDto = new UserDto("Test", "test@test.test");
        Assertions.assertThrows(NotFoundException.class, () -> userService.update(userDto, 1));
    }

    @Test
    public void testUpdateInvalid() {
        User user = new User("test", "test@test.test");
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        UserDto userDto = new UserDto("Test", "test");

        Assertions.assertThrows(ValidationException.class, () -> userService.update(userDto, 1));
    }

    @Test
    public void testUpdateSuccess() {
        User user = new User("test", "test@test.test");
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        UserDto userDto = new UserDto("Test", "test@updated.test");

        UserDto updated = userService.update(userDto, 1);
        userDto.setItems(new ArrayList<>());
        Assertions.assertEquals(userDto, updated);
    }

    @Test
    public void testGetNotFound() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> userService.get(1));
    }

    @Test
    public void testGetAll() {
        List<UserDto> updated = new ArrayList<>();
        User user = new User("test", "test@test.test");
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        updated.add(UserMapper.toDto(user));
        List<UserDto> result = userService.getAll();

        Assertions.assertIterableEquals(updated, result);

    }

}
