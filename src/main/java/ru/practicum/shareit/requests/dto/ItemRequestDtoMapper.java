package ru.practicum.shareit.requests.dto;

import ru.practicum.shareit.requests.model.ItemRequest;

public class ItemRequestDtoMapper {
    public static ItemRequestDto toDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getDescription(), itemRequest.getAuthor(), itemRequest.getSuggestions(),
                itemRequest.getStatus(), itemRequest.getCreated_at());
    }

    public static ItemRequest fromDto(ItemRequestDto itemRequestDto) {
        return new ItemRequest(itemRequestDto.getAuthor(), itemRequestDto.getDescription(),
                itemRequestDto.getStatus());
    }
}
