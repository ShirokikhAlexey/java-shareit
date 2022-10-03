package ru.practicum.shareit.booking.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "booking", schema = "public")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinTable(name="items", joinColumns=@JoinColumn(name="id"))
    private Item item;

    @NonNull
    @ManyToOne
    @JoinTable(name="users", joinColumns=@JoinColumn(name="id"))
    private User bookedBy;

    @NonNull
    @Column(name = "from_timestamp", nullable = false)
    private LocalDateTime from;

    @NonNull
    @Column(name = "to_timestamp", nullable = false)
    private LocalDateTime to;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    @Column(name = "review", nullable = false)
    private String review;
}
