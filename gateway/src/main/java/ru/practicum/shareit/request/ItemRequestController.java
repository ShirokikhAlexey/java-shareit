package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @RequestBody @Valid ItemRequestDto itemRequest) {
        return itemRequestClient.addItemRequest(userId, itemRequest);
    }

    @GetMapping
    public ResponseEntity<Object> getUserAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestClient.getUserItemRequest(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @PathVariable Long requestId) {
        return itemRequestClient.getItemRequest(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return itemRequestClient.searchItemRequest(userId, from, size);
    }
}
