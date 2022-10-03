package ru.practicum.shareit.booking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto {
    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("item_id")
    private Integer item_id;

    @JsonProperty("from")
    private LocalDateTime from;

    @JsonProperty("to")
    private LocalDateTime to;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("review")
    private String review;

    public BookingDto(Integer user_id, Integer item_id, LocalDateTime from, LocalDateTime to, Status status,
                      String review) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.from = from;
        this.to = to;
        this.status = status;
        this.review = review;
    }
}
