package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.util.Status;
import ru.practicum.shareit.user.User;

import java.util.List;


@Data
@RequiredArgsConstructor
public class Item {
    @NonNull
    private Integer id;
    @NonNull
    private User owner;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Status status;
    @NonNull
    private List<Booking> booking;
}
