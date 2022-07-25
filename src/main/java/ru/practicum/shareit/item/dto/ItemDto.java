package ru.practicum.shareit.item.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.util.Status;

@Data
@RequiredArgsConstructor
public class ItemDto {
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Status status;
}
