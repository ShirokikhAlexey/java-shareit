package ru.practicum.shareit.requests;

import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequestDto item, Integer userId);

    ItemRequestDto get(Integer itemRequestId, Integer userId);

    List<ItemRequestDto> getAll(Integer userId, Integer from, Integer size);

    List<ItemRequestDto> getUserAll(Integer itemRequestId);
}
