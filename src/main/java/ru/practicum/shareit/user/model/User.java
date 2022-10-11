package ru.practicum.shareit.user.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "bookedBy")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    public User() {

    }
}
