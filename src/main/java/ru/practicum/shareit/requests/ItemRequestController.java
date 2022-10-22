package ru.practicum.shareit.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.requests.dto.ItemRequestDto;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Autowired
    private ItemRequestService itemRequestService;

    @PostMapping()
    public ItemRequestDto create(@RequestBody ItemRequestDto itemRequest,
                                 @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.create(itemRequest, userId);
    }

    @GetMapping()
    public List<ItemRequestDto> getUserAll(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemRequestService.getUserAll(userId);
    }

    @GetMapping(value = "/{requestId}")
    public ItemRequestDto get(@PathVariable int requestId) {
        return itemRequestService.get(requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> search(@RequestParam(defaultValue = "0", required = false) Integer from,
                                       @RequestParam(defaultValue = "10", required = false) Integer size) {
        return itemRequestService.getAll(from, size);
    }


}
