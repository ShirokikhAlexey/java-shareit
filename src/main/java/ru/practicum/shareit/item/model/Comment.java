package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @NonNull
    @Column(name = "review", nullable = false)
    private String review;

    public Comment(User author, Item item, String review) {
        this.author = author;
        this.item = item;
        this.review = review;
    }
}