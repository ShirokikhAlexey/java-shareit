package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentDto {
    private Integer id;

    @JsonProperty(value = "user_id")
    private Integer userId;

    @JsonProperty(value = "item_id")
    private Integer itemId;

    private String text;

    private String authorName;
    private LocalDateTime created;

    public CommentDto(Integer userId, Integer itemId, String text) {
        this.userId = userId;
        this.itemId = itemId;
        this.text = text;
    }

    public CommentDto(String text) {
        this.text = text;
    }

    public CommentDto(Integer id, Integer userId, Integer itemId,
                      String text, String authorName, LocalDateTime created) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.text = text;
        this.authorName = authorName;
        this.created = created;
    }
}
