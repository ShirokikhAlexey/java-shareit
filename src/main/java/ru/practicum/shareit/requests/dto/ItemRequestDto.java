package ru.practicum.shareit.requests.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ItemRequestDto {
    @JsonProperty("description")
    private String description;

    private User author;

    private List<Item> suggestions;

    @JsonProperty("status")
    private Status status = Status.OPEN;

    @JsonProperty("createdAt")
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
