package ru.practicum.shareit.db.base;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorageBase {
    Item get(Integer id);

    Item create(Item item);

    Item update(Item item);

    void delete(Integer id);

    List<Item> getUserItems(Integer userId);

    List<Item> search(String text);
}
