package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDto {
    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("text")
    private String review;

    public CommentDto(Integer userId, Integer itemId, String text) {
        this.userId = userId;
        this.itemId = itemId;
        this.review = text;
    }

    public CommentDto(String text) {
        this.review = text;
    }
}
