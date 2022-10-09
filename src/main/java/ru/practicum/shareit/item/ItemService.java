package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto item, Integer userId) throws NotFoundException;

    ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException, InvalidUserException;

    ItemDto get(Integer itemId) throws NotFoundException;

    List<ItemDto> getAll(Integer itemId);

    List<ItemDto> search(String text);

    void addComment(CommentDto comment) throws NotFoundException, InvalidUserException;

    List<CommentDto> getItemComments(Integer itemId);

}
