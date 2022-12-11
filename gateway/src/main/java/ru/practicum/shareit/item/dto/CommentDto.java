package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentDto {
    private Integer id;

    private Integer user_id;

    private Integer item_id;

    private String text;

    private String authorName;
    private LocalDateTime created;

    public CommentDto(Integer user_id, Integer item_id, String text) {
        this.user_id = user_id;
        this.item_id = item_id;
        this.text = text;
    }

    public CommentDto(String text) {
        this.text = text;
    }

    public CommentDto(Integer id, Integer user_id, Integer item_id,
                      String text, String authorName, LocalDateTime created) {
        this.id = id;
        this.user_id = user_id;
        this.item_id = item_id;
        this.text = text;
        this.authorName = authorName;
        this.created = created;
    }
}
