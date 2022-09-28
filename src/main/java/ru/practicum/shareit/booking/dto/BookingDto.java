package ru.practicum.shareit.booking.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto {
    @NonNull
    private Item item;
    @NonNull
    private User bookedBy;
    @NonNull
    private LocalDateTime from;
    @NonNull
    private LocalDateTime to;
    @NonNull
    private Boolean finished;
    @NonNull
    private String review;
}
