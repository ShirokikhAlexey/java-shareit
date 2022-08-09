package ru.practicum.shareit.db.memory;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.db.base.ItemStorageBase;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ItemStorageMemory implements ItemStorageBase {
    private final HashMap<Integer, Item> itemsList = new HashMap<>();
    private int counter = 0;

    @Override
    public Item get(Integer id) {
        return itemsList.get(id);
    }

    @Override
    public Item create(Item item) {
        ++counter;
        item.setId(counter);
        itemsList.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item) {
        itemsList.put(item.getId(), item);
        return item;
    }

    @Override
    public void delete(Integer id) {
        itemsList.remove(id);
    }

    @Override
    public List<Item> getUserItems(Integer userId) {
        List<Item> userItems = new ArrayList<>();
        for (Item item : itemsList.values()) {
            if (item.getOwner().getId().equals(userId)) {
                userItems.add(item);
            }
        }
        return userItems;
    }

    @Override
    public List<Item> search(String text) {
        List<Item> userItems = new ArrayList<>();
        if (text.isBlank()) {
            return userItems;
        }
        for (Item item : itemsList.values()) {
            if ((item.getName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase()))
                    && item.getAvailable()) {
                userItems.add(item);
            }
        }
        return userItems;
    }
}

