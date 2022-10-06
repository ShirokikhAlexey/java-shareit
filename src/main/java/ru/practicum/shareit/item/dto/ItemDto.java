package ru.practicum.shareit.item.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ItemDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("available")
    private Boolean available;

    private LocalDateTime latestBooking;

    private LocalDateTime nearestBooking;


    public ItemDto(Integer id, String name, String description, Boolean available, LocalDateTime latestBooking,
                   LocalDateTime nearestBooking) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.latestBooking = latestBooking;
        this.nearestBooking = nearestBooking;
    }

    public ItemDto(Integer id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
