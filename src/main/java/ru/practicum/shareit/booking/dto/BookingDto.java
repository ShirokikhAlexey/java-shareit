package ru.practicum.shareit.booking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto {
    private Integer id;
    private Integer bookerId;
    private Integer itemId;

    @JsonProperty("start")
    private LocalDateTime from;

    @JsonProperty("end")
    private LocalDateTime to;

    private Status status = Status.WAITING;
    private String review;
    private UserDto booker;
    private ItemDto item;

    public BookingDto(Integer id, Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, Status status,
                      String review) {
        this.id = id;
        this.bookerId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = status;
        this.review = review;
    }

    public BookingDto(Integer id, Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, String status,
                      String review) {
        this.id = id;
        this.bookerId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = Status.valueOf(status);
        this.review = review;
    }

    public BookingDto(Integer id, Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, String status,
                      String review, UserDto booker, ItemDto item) {
        this.id = id;
        this.bookerId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = Status.valueOf(status);
        this.review = review;
        this.booker = booker;
        this.item = item;
    }

    public BookingDto(Integer id, Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, Status status,
                      String review, UserDto booker, ItemDto item) {
        this.id = id;
        this.bookerId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = status;
        this.review = review;
        this.booker = booker;
        this.item = item;
    }

    public BookingDto(Integer userId, Integer itemId, LocalDateTime from, LocalDateTime to, Status status,
                      String review) {
        this.bookerId = userId;
        this.itemId = itemId;
        this.from = from;
        this.to = to;
        this.status = status;
        this.review = review;
    }

    public BookingDto(Status status, String review) {
        this.status = status;
        this.review = review;
    }
}
