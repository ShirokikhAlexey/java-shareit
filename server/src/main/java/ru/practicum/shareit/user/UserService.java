package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto user) throws InvalidUserParameters;

    UserDto update(UserDto userDto, Integer userId) throws NotFoundException;

    UserDto get(Integer userId) throws NotFoundException;

    void delete(Integer userId) throws NotFoundException;

    List<UserDto> getAll();
}
