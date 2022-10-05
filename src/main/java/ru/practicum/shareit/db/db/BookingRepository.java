package ru.practicum.shareit.db.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("select new ru.practicum.shareit.booking.BookingDto(b.booked_by_id, b.item_id, b.from_timestamp, b.to_timestamp, b.status, b.review) " +
            "from Booking as b " +
            "where (upper(b.status) = upper('?2') or upper(?2) = 'ALL') " +
            "and b.booked_by_id = ?1 " +
            "order by b.from_timestamp desc")
    List<BookingDto> getByStatus(Integer userId, String state);

    @Query("select new ru.practicum.shareit.booking.BookingDto(b.booked_by_id, b.item_id, b.from_timestamp, b.to_timestamp, b.status, b.review) " +
            "from Booking as b " +
            "join Item as i on i.id = b.item_id " +
            "where (upper(b.status) = upper('?2') or upper(?2) = 'ALL') " +
            "and i.owner_id = ?1 " +
            "order by b.from_timestamp desc")
    List<BookingDto> getUserItemsBookings(Integer userId, String state);
}
