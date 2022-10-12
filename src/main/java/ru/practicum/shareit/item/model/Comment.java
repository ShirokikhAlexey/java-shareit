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
    @JoinTable(name = "users", joinColumns = @JoinColumn(name = "id"))
    private User author;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinTable(name = "items", joinColumns = @JoinColumn(name = "id"))
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