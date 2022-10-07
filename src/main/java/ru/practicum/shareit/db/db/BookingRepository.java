package ru.practicum.shareit.db.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select new ru.practicum.shareit.booking.BookingDto(b.booked_by_id, b.item_id, b.from_timestamp, " +
            "b.to_timestamp, b.status, b.review) " +
            "from Booking as b " +
            "where (upper(b.status) = upper('?2') or upper(?2) = 'ALL') " +
            "and b.booked_by_id = ?1 " +
            "order by b.from_timestamp desc", nativeQuery = true)
    List<BookingDto> getByStatus(Integer userId, String state);

    @Query(value = "select new ru.practicum.shareit.booking.BookingDto(b.booked_by_id, b.item_id, b.from_timestamp, " +
            "b.to_timestamp, b.status, b.review) " +
            "from Booking as b " +
            "join Item as i on i.id = b.item_id " +
            "where (upper(b.status) = upper('?2') or upper(?2) = 'ALL') " +
            "and i.owner_id = ?1 " +
            "order by b.from_timestamp desc", nativeQuery = true)
    List<BookingDto> getUserItemsBookings(Integer userId, String state);

    @Query(value = "select b.to_timestamp " +
            "from Booking as b " +
            "where b.item_id = ?1 " +
            "and b.status = 'PAST' " +
            "order by b.from_timestamp desc " +
            "limit 1", nativeQuery = true)
    LocalDateTime getItemLatestBooking(Integer itemId);

    @Query(value = "select b.to_timestamp " +
            "from Booking as b " +
            "where b.item_id = ?1 " +
            "and b.status = 'FUTURE' " +
            "order by b.from_timestamp asc " +
            "limit 1", nativeQuery = true)
    LocalDateTime getItemNearestBooking(Integer itemId);
}
