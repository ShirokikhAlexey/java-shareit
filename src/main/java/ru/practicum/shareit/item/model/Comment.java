package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "comments", schema = "public")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinTable(name = "users", joinColumns = @JoinColumn(name = "id"))
    private User author;

    @NonNull
    @ManyToOne
    @JoinTable(name = "items", joinColumns = @JoinColumn(name = "id"))
    private Item item;

    @NonNull
    @Column(name = "review", nullable = false)
    private String review;
}