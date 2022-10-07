package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users", schema = "public")
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

    public User(){}
}
