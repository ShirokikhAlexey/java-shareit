package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping()
    public ItemDto create(@RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        ItemDto newItem = itemService.create(item, userId);
        log.info("Добавлена новая вещь {}", newItem.toString());
        return newItem;
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto update(@PathVariable int itemId, @RequestBody ItemDto item,
                          @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.update(itemId, item, userId);
    }

    @GetMapping(value = "/{itemId}")
    public ItemDto get(@PathVariable int itemId,
                       @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.get(itemId, userId);
    }

    @GetMapping()
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.getAll(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

    @PostMapping(value = "/{itemId}/comment")
    public CommentDto addComment(@RequestBody CommentDto commentDto, @PathVariable int itemId,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        commentDto.setItemId(itemId);
        commentDto.setUserId(userId);
        return itemService.addComment(commentDto);
    }
}
