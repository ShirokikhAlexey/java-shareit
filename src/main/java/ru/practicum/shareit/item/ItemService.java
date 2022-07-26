package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    public ItemDto create(ItemDto item, Integer userId);

    public ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException, InvalidUserException;

    public ItemDto get(Integer itemId) throws NotFoundException;

    public List<ItemDto> getAll(Integer itemId);

    public List<ItemDto> search(String text);

}
