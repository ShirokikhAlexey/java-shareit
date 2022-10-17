package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @NonNull
    @ManyToOne
    @JoinColumn(name = "booked_by_id")
    private User bookedBy;

    @NonNull
    @Column(name = "from_timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime from;

    @NonNull
    @Column(name = "to_timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime to;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "review")
    private String review;

    public Booking(Item item, User bookedBy, LocalDateTime from, LocalDateTime to, String status,
                   String review) {
        this.bookedBy = bookedBy;
        this.from = from;
        this.to = to;
        this.item = item;
        this.status = Status.valueOf(status);
        this.review = review;
    }

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
