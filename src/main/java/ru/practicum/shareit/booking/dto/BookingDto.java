package ru.practicum.shareit.booking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.util.Status;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("itemId")
    private Integer itemId;

    @JsonProperty("start")
    private LocalDateTime from;

    @JsonProperty("end")
    private LocalDateTime to;

    @JsonProperty("status")
    private Status status = Status.WAITING;

    @JsonProperty("review")
    private String review;

    public BookingDto(Integer id, Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, Status status,
                      String review) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = status;
        this.review = review;
    }

    public BookingDto(Integer id , Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, String status,
                      String review) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = Status.valueOf(status);
        this.review = review;
    }
}
