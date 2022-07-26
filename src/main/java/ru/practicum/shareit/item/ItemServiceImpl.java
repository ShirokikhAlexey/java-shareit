package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.ShareItApp.itemStorage;
import static ru.practicum.shareit.ShareItApp.userStorage;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public ItemDto create(ItemDto item, Integer userId) {
        User user = userStorage.get(userId);
        return ItemMapper.toDto(itemStorage.create(ItemMapper.fromDto(user, item)));
    }

    @Override
    public ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException, InvalidUserException {
        Item item = itemStorage.get(itemId);
        if (item == null) {
            throw new NotFoundException();
        }
        if (!item.getOwner().getId().equals(userId)) {
            throw new InvalidUserException();
        }
        Item updatedItem = ItemMapper.updateFromDto(item, itemDto);
        itemStorage.update(updatedItem);
        return ItemMapper.toDto(updatedItem);
    }

    @Override
    public ItemDto get(Integer itemId) throws NotFoundException {
        Item item = itemStorage.get(itemId);
        if(item == null){
            throw new NotFoundException();
        }
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAll(Integer itemId) {
        List<ItemDto> result = new ArrayList<>();
        for(Item item : itemStorage.getUserItems(itemId)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }

    @Override
    public List<ItemDto> search(String text) {
        List<ItemDto> result = new ArrayList<>();
        for(Item item : itemStorage.search(text)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }
}
