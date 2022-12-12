package ru.practicum.shareit.request.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.util.Status;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ItemRequestDto {
    private Integer id;
    private String description;

    private UserDto author;

    @JsonProperty("items")
    private List<ItemDto> suggestions;

    private Status status = Status.OPEN;

    @JsonProperty("created")
    private LocalDateTime createdAt = LocalDateTime.now();

    public ItemRequestDto(String description, UserDto author, List<ItemDto> suggestions,
                          Status status, LocalDateTime createdAt) {
        this.author = author;
        this.suggestions = suggestions;
        this.status = status;
        this.createdAt = createdAt;
        this.description = description;
    }

    public ItemRequestDto(Integer id, String description, UserDto author, List<ItemDto> suggestions,
                          Status status, LocalDateTime createdAt) {
        this.author = author;
        this.suggestions = suggestions;
        this.status = status;
        this.createdAt = createdAt;
        this.description = description;
        this.id = id;
    }

    public ItemRequestDto(String description) {
        this.description = description;
    }
}
