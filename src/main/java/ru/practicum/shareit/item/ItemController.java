package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ItemDto create(@RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        ItemDto newItem = itemService.create(item, userId);
        log.info("Добавлена новая вещь {}", newItem.toString());
        return newItem;
    }

    @PatchMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ItemDto update(@PathVariable int itemId, @RequestBody ItemDto item,
                       @RequestHeader("X-Sharer-User-Id") Integer userId) {
        try {
            return itemService.update(itemId, item, userId);
        } catch (NotFoundException e) {
            log.info("Попытка обновления несуществующей записи: {}", item.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InvalidUserException e) {
            log.info("Попытка изменения записи не владельцем: {}", item.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{itemId}")
    public ItemDto get(@PathVariable int itemId) {
        try {
            return itemService.get(itemId);
        } catch (NotFoundException e) {
            log.info("Запись не найдена: {}", itemId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Integer userId){
        return itemService.getAll(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(String text){
        return itemService.search(text);
    }
}
