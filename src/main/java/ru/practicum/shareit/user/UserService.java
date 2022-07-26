package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    public UserDto create(UserDto user) throws InvalidUserException, InvalidUserParameters;

    public UserDto update(UserDto userDto, Integer userId) throws NotFoundException, InvalidUserException;

    public UserDto get(Integer userId) throws NotFoundException;

    public void delete(Integer userId) throws NotFoundException;

    public List<UserDto> getAll();
}
