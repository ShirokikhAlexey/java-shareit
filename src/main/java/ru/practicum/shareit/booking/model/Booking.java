package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.item.model.Item;
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

    @JsonIgnore
    @NonNull
    @ManyToOne
    private Item item;

    @JsonIgnore
    @NonNull
    @ManyToOne
    private User bookedBy;

    @NonNull
    @Column(name = "from_timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime from;

    @NonNull
    @Column(name = "to_timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime to;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "review", nullable = true)
    private String review;

    public Booking(Item item, User bookedBy, LocalDateTime from, LocalDateTime to, Status status,
                   String review) {
        this.bookedBy = bookedBy;
        this.from = from;
        this.to = to;
        this.item = item;
        this.status = status;
        this.review = review;
    }

    public Booking() {

    }
}
