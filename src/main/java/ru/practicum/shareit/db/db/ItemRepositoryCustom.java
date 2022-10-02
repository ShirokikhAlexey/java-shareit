package ru.practicum.shareit.db.db;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> search(String text);
}
