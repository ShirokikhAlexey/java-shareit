package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
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
        try {
            UserDto newUser = userService.create(user);
            log.info("Добавлен новый пользователь {}", newUser.toString());
            return newUser;
        } catch (InvalidUserException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidUserParameters | ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{userId}")
    public UserDto update(@PathVariable int userId, @RequestBody UserDto user) {
        try {
            return userService.update(user, userId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (InvalidUserException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userId}")
    public UserDto get(@PathVariable int userId) {
        try {
            return userService.get(userId);
        } catch (NotFoundException e) {
            log.info("Запись не найдена: {}", userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable int userId) {
        try {
            userService.delete(userId);
        } catch (NotFoundException e) {
            log.info("Запись не найдена: {}", userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }
}
