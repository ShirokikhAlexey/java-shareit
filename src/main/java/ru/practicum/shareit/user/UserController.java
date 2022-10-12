package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.ValidationException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public UserDto create(@RequestBody UserDto user) {
        UserDto newUser = userService.create(user);
        log.info("Добавлен новый пользователь {}", newUser.toString());
        return newUser;
    }

    @PatchMapping(value = "/{userId}")
    public UserDto update(@PathVariable int userId, @RequestBody UserDto user) {
        return userService.update(user, userId);
    }

    @GetMapping(value = "/{userId}")
    public UserDto get(@PathVariable int userId) {
        return userService.get(userId);
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable int userId) {
        userService.delete(userId);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }
}
