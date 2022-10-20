package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("text")
    private String review;

    @JsonProperty("authorName")
    private String authorName;

    @JsonProperty("created")
    private LocalDateTime created;

    public CommentDto(Integer userId, Integer itemId, String text) {
        this.userId = userId;
        this.itemId = itemId;
        this.review = text;
    }

    public CommentDto(String text) {
        this.review = text;
    }

    public CommentDto(Integer id, Integer userId, Integer itemId,
                      String text, String authorName, LocalDateTime created) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.review = text;
        this.authorName = authorName;
        this.created = created;
    }
}
