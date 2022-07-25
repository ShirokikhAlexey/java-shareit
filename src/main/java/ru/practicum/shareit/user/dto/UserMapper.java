package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getItems());
    }
}
