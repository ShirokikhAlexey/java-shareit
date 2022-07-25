package ru.practicum.shareit.user.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserDto {
    @NonNull
    private String name;
    @NonNull
    private List<Item> items;
}
