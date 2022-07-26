package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.shareit.ShareItApp.itemStorage;
import static ru.practicum.shareit.ShareItApp.userStorage;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public ItemDto create(ItemDto item, Integer userId) throws NotFoundException {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        validate(item);
        return ItemMapper.toDto(itemStorage.create(ItemMapper.fromDto(user, item)));
    }

    @Override
    public ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException, InvalidUserException {
        Item item = itemStorage.get(itemId);
        if (item == null) {
            throw new NotFoundException();
        }
        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException();
        }
        Item updatedItem = ItemMapper.updateFromDto(item, itemDto);
        itemStorage.update(updatedItem);
        return ItemMapper.toDto(updatedItem);
    }

    @Override
    public ItemDto get(Integer itemId) throws NotFoundException {
        Item item = itemStorage.get(itemId);
        if (item == null) {
            throw new NotFoundException();
        }
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAll(Integer itemId) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemStorage.getUserItems(itemId)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }

    @Override
    public List<ItemDto> search(String text) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemStorage.search(text)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }

    private void validate(ItemDto item) {
        if (item.getAvailable() == null) {
            throw new ValidationException();
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException();
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException();
        }
    }
}
