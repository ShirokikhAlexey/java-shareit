package ru.practicum.shareit.requests.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemRequestDto {
    private String description;

    private User author;

    private List<Item> suggestions;

    private Status status;

    private LocalDateTime createdAt;

    public ItemRequestDto(String description, User author, List<Item> suggestions,
                          Status status, LocalDateTime createdAt) {
        this.author = author;
        this.suggestions = suggestions;
        this.status = status;
        this.createdAt = createdAt;
        this.description = description;
    }

    public ItemRequestDto(String description) {
        this.description = description;
    }
}
