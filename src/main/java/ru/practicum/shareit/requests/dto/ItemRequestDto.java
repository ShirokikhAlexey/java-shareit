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

    private User author;

    private List<Item> suggestions;

    private Status status;
}
