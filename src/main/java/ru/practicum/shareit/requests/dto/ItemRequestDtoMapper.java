package ru.practicum.shareit.requests.dto;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemRequestDtoMapper {
    public static ItemRequestDto toDto(ItemRequest itemRequest) {
        List<ItemDto> items = new ArrayList<>();
        if (itemRequest.getSuggestions() != null && !itemRequest.getSuggestions().isEmpty()) {
            for (Item item : itemRequest.getSuggestions()) {
                if (item != null) {
                    items.add(ItemMapper.toDto(item));
                }
            }
        }
        return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(),
                UserMapper.toDto(itemRequest.getAuthor()), items,
                itemRequest.getStatus(), itemRequest.getCreatedAt());
    }

    public static ItemRequest fromDto(ItemRequestDto itemRequestDto, User author) {
        return new ItemRequest(author, itemRequestDto.getDescription(),
                itemRequestDto.getStatus(), LocalDateTime.now());
    }
}
