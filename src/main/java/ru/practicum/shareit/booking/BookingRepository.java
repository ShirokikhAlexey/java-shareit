package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select new ru.practicum.shareit.booking.dto.BookingDto(b.item.id, b.bookedBy.id, b.from, b.to, b.status, b.review) " +
            "from Booking as b " +
            "where (upper(b.status) = upper(?2) or upper(?2) = 'ALL') " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc")
    List<BookingDto> getByStatus(Integer userId, String state);

    @Query(value = "select new ru.practicum.shareit.booking.dto.BookingDto(b.item.id, b.bookedBy.id, b.from, b.to, b.status, b.review) " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where (upper(b.status) = upper(?2) or upper(?2) = 'ALL') " +
            "and i.owner.id = ?1 " +
            "order by b.from desc")
    List<BookingDto> getUserItemsBookings(Integer userId, String state);

    @Query(value = "select b.to_timestamp " +
            "from booking as b " +
            "where b.item_id = ?1 " +
            "and b.status = 'PAST' " +
            "order by b.from_timestamp desc " +
            "limit 1", nativeQuery = true)
    LocalDateTime getItemLatestBooking(Integer itemId);

    @Query(value = "select b.to_timestamp " +
            "from booking as b " +
            "where b.item_id = ?1 " +
            "and b.status = 'FUTURE' " +
            "order by b.from_timestamp asc " +
            "limit 1", nativeQuery = true)
    LocalDateTime getItemNearestBooking(Integer itemId);

    @Query(value = "select new ru.practicum.shareit.booking.dto.BookingDto(b.item.id, b.bookedBy.id, b.from, b.to, b.status, b.review) " +
            "from Booking as b " +
            "where b.item.id  = ?2 " +
            "and b.bookedBy.id = ?1 " +
            "and b.status in ('PAST', 'CURRENT')")
    BookingDto getUserItemBooking(Integer userId, Integer itemId);
}
