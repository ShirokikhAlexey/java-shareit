package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getAuthor().getId(), comment.getItem().getId(),
                comment.getReview(), comment.getAuthor().getName(), comment.getCreatedAt());
    }

    public static Comment fromDto(User user, Item item, CommentDto commentDto) {
        return new Comment(user, item, commentDto.getReview(), LocalDateTime.now());
    }
}
