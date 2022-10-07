package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentDto {
    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("item_id")
    private Integer item_id;

    @JsonProperty("review")
    private String review;

    public CommentDto(Integer user_id, Integer item_id, String review) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.review = review;
    }
}
