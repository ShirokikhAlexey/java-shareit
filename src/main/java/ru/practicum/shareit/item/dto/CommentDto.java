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

    @JsonProperty("review")
    private String review;

    public CommentDto(Integer userId, Integer itemId, String review) {
        this.userId = userId;
        this.itemId = itemId;
        this.review = review;
    }
}
