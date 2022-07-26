package ru.practicum.shareit.db.base;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorageBase {
    public Item get(Integer id);

    public Item create(Item item);

    public Item update(Item item);

    public void delete(Integer id);

    public List<Item> getUserItems(Integer userId);

    public List<Item> search(String text);
}
