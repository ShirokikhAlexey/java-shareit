package ru.practicum.shareit.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Autowired
    private ItemRequestService itemRequestService;

    @PostMapping()
    public ItemRequestDto create(@RequestBody ItemRequestDto itemRequest,
                                 @RequestHeader("X-Sharer-User-Id") Integer userId) {
        ItemRequestDto newItemRequest = itemRequestService.create(itemRequest, userId);
        log.info("Добавлен новый запрос на вещь {}", newItemRequest.toString());
        return newItemRequest;
    }

    @GetMapping()
    public List<ItemRequestDto> getAll(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getAll(userId);
    }


}
