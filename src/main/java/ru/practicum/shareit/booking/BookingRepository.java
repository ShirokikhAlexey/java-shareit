package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select b " +
            "from Booking as b " +
            "where (upper(b.status) = upper(?2) or upper(?2) = 'ALL') " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc")
    List<Booking> getByStatus(Integer userId, String state);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.from > now() " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc")
    List<Booking> getFuture(Integer userId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.to < now() " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc")
    List<Booking> getPast(Integer userId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.from < now() and b.to > now() " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc")
    List<Booking> getCurrent(Integer userId);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.from > now() " +
            "and i.owner.id = ?1 " +
            "order by b.from desc")
    List<Booking> getOwnerFuture(Integer userId);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.to < now() " +
            "and i.owner.id = ?1 " +
            "order by b.from desc")
    List<Booking> getOwnerPast(Integer userId);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.from < now() and b.to > now() " +
            "and i.owner.id = ?1 " +
            "order by b.from desc")
    List<Booking> getOwnerCurrent(Integer userId);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where (upper(b.status) = upper(?2) or upper(?2) = 'ALL') " +
            "and i.owner.id = ?1 " +
            "order by b.from desc")
    List<Booking> getUserItemsBookings(Integer userId, String state);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id = ?1 " +
            "and b.status = 'PAST' " +
            "order by b.from desc ")
    List<Booking> getItemLatestBooking(Integer itemId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id = ?1 " +
            "and b.status = 'FUTURE' " +
            "order by b.from asc ")
    List<Booking> getItemNearestBooking(Integer itemId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id  = ?2 " +
            "and b.bookedBy.id = ?1 " +
            "and b.status in ('PAST', 'CURRENT')")
    Booking getUserItemBooking(Integer userId, Integer itemId);
}
