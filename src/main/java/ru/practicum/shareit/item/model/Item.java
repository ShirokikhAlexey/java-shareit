package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.List;


@Data
@RequiredArgsConstructor
@Entity
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinTable(name="users", joinColumns=@JoinColumn(name="id"))
    private User owner;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @NonNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @ElementCollection
    @CollectionTable(name="booking", joinColumns=@JoinColumn(name="item_id"))
    private List<Booking> booking;
}
