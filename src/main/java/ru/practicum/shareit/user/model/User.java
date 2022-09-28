package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class User {
    private Integer id;
    @NonNull
    private String name;

    @NonNull
    private String email;

    private List<Item> items = new ArrayList<>();
}
