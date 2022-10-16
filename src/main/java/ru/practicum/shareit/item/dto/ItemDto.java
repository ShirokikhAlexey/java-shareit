package ru.practicum.shareit.item.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDateTime;
import java.util.List;

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

    private BookingDto latestBooking;

    private BookingDto nearestBooking;

    @JsonIgnore
    private List<CommentDto> comments;


    public ItemDto(Integer id, String name, String description, Boolean available, BookingDto latestBooking,
                   BookingDto nearestBooking) {
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
