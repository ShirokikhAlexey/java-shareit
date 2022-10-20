package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

public interface ItemService {
    ItemRequestDto create(ItemRequestDto item, Integer userId);

    ItemRequestDto update(Integer itemRequestId, ItemRequestDto itemRequestDto, Integer userId);

    ItemRequestDto get(Integer itemRequestId);
}
