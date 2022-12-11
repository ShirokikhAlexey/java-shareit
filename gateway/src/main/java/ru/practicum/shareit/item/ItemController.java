package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @RequestBody @Valid ItemDto itemDto) {
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping(value = "/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable int itemId, @RequestBody ItemDto item,
                                             @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemClient.updateItem(userId, itemId, item);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                          @PathVariable Long itemId) {
        return itemClient.getItem(userId, itemId);
    }


    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @Positive @RequestParam(name = "from", defaultValue = "1") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return itemClient.getItems(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getUserItemsBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                       @RequestParam(name = "text") String text,
                                                       @Positive @RequestParam(name = "from", defaultValue = "1") Integer from,
                                                       @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return itemClient.searchItems(userId, text, from, size);
    }

    @PostMapping(value = "/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto commentDto, @PathVariable int itemId,
                                             @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemClient.addComment(userId, itemId, commentDto);
    }
}
