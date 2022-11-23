package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto item, Integer userId) throws NotFoundException;

    ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException;

    ItemDto get(Integer itemId, Integer userId) throws NotFoundException;

    List<ItemDto> getAll(Integer itemId, Integer from, Integer size);

    List<ItemDto> search(String text, Integer from, Integer size);

    CommentDto addComment(CommentDto comment) throws NotFoundException;

    List<CommentDto> getItemComments(Integer itemId);

}
