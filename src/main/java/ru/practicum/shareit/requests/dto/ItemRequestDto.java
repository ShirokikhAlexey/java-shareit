package ru.practicum.shareit.requests.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ItemRequestDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("description")
    private String description;

    private UserDto author;

    @JsonProperty("items")
    private List<ItemDto> suggestions;

    @JsonProperty("status")
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
