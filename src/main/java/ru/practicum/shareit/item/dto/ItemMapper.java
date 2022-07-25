package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toDto(Item item) {
        return new ItemDto(item.getName(), item.getDescription(), item.getStatus());
    }
}
