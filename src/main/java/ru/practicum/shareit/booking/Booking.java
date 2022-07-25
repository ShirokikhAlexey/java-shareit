package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class Booking {
    @NonNull
    private Integer id;
    @NonNull
    private Item item;
    @NonNull
    private User bookedBy;
    @NonNull
    private LocalDateTime from;
    @NonNull
    private LocalDateTime to;
    @NonNull
    private Boolean finished = false;
    @NonNull
    private String review;
}
