package ru.practicum.shareit.booking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.util.Status;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto {
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("from")
    private LocalDateTime from;

    @JsonProperty("to")
    private LocalDateTime to;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("review")
    private String review;

    public BookingDto(Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, Status status,
                      String review) {
        this.userId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = status;
        this.review = review;
    }
}
