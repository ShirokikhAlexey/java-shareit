package ru.practicum.shareit.item.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ItemDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private Integer requestId;

    private BookItemRequestDto lastBooking;

    private BookItemRequestDto nextBooking;
    private List<CommentDto> comments;


    public ItemDto(Integer id, String name, String description, Boolean available, BookItemRequestDto latestBooking,
                   BookItemRequestDto nearestBooking) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.lastBooking = latestBooking;
        this.nextBooking = nearestBooking;
    }

    public ItemDto(Integer id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public ItemDto(Integer id, String name, String description, Boolean available, Integer requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

    public ItemDto(String name, String description, Boolean available, Integer requestId) {
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }
}
