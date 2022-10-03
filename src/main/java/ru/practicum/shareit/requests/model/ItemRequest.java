package ru.practicum.shareit.requests.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "item_requests", schema = "public")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinTable(name="users", joinColumns=@JoinColumn(name="id"))
    private User author;

    @NonNull
    @ManyToOne
    @JoinTable(name="items", joinColumns=@JoinColumn(name="id"))
    private Item suggestion;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
}