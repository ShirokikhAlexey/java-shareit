package ru.practicum.shareit.requests.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ItemRequestDto {
    @NonNull
    private User author;
    @NonNull
    private List<Item> suggestions;
    @NonNull
    private Status closed;
}
