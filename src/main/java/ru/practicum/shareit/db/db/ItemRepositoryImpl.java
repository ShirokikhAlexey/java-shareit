package ru.practicum.shareit.db.db;

import org.springframework.context.annotation.Lazy;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final ItemRepository itemRepository;

    public ItemRepositoryImpl(@Lazy ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> search(String text) {
        List<Integer> ids = itemRepository.searchIds(text);
        return itemRepository.findAllById(ids);
    }
}
