package ru.practicum.shareit.requests.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @NonNull
    @JoinColumn(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToMany()
    @JoinTable(
            name = "item_suggestions",
            joinColumns = { @JoinColumn(name = "request_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private List<Item> suggestions;

    public ItemRequest(User author, String description, Status status, LocalDateTime createdAt) {
        this.author = author;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ItemRequest(Integer id, User author, String description, Status status,
                       LocalDateTime createdAt, List<Item> suggestions) {
        this.author = author;
        this.description = description;
        this.status = status;
        this.id = id;
        this.createdAt = createdAt;
        this.suggestions = suggestions;
    }

    public ItemRequest() {

    }

}
