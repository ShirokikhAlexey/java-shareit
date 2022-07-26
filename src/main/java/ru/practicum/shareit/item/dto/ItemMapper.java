package ru.practicum.shareit.item.dto;

import lombok.NonNull;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.util.Status;
import ru.practicum.shareit.user.User;

public class ItemMapper {
    public static ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getStatus());
    }

    public static Item fromDto(User user, ItemDto itemDto) {
        return new Item(user, itemDto.getName(), itemDto.getDescription(), itemDto.getStatus());
    }

    public static Item updateFromDto(Item item, ItemDto itemDto) {
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getStatus() != null) {
            item.setStatus(itemDto.getStatus());
        }
        return item;
    }
}
