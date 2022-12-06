package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public UserDto create(@RequestBody UserDto user) {
        return userService.create(user);
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
