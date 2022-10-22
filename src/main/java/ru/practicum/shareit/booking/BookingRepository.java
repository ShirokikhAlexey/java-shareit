package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.util.Status;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select b " +
            "from Booking as b " +
            "where (b.status = ?2 or ?2 = 'ALL') " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc " +
            "limit ?4 " +
            "offset ?3 ")
    List<Booking> getByStatus(Integer userId, Status state, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.from > now() " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc " +
            "limit ?3 " +
            "offset ?2 ")
    List<Booking> getFuture(Integer userId, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.to < now() " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc " +
            "limit ?3 " +
            "offset ?2 ")
    List<Booking> getPast(Integer userId, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.from <= now() and b.to >= now() " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc " +
            "limit ?3 " +
            "offset ?2 ")
    List<Booking> getCurrent(Integer userId, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.from > now() " +
            "and i.owner.id = ?1 " +
            "order by b.from desc " +
            "limit ?3 " +
            "offset ?2 ")
    List<Booking> getOwnerFuture(Integer userId, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.to < now() " +
            "and i.owner.id = ?1 " +
            "order by b.from desc " +
            "limit ?3 " +
            "offset ?2 ")
    List<Booking> getOwnerPast(Integer userId, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.from <= now() and b.to >= now() " +
            "and i.owner.id = ?1 " +
            "order by b.from desc " +
            "limit ?3 " +
            "offset ?2 ")
    List<Booking> getOwnerCurrent(Integer userId, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where (b.status = ?2 or ?2 = 'ALL') " +
            "and i.owner.id = ?1 " +
            "order by b.from desc " +
            "limit ?4 " +
            "offset ?3 ")
    List<Booking> getUserItemsBookings(Integer userId, Status state, Integer from, Integer size);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id = ?1 " +
            "and b.to <= now() " +
            "and b.status not in ('WAITING', 'REJECTED', 'CURRENT', 'FUTURE') " +
            "order by b.to desc ")
    List<Booking> getItemLatestBooking(Integer itemId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id = ?1 " +
            "and b.from >= now() " +
            "and b.status not in ('WAITING', 'REJECTED', 'CURRENT', 'PAST') " +
            "order by b.from asc ")
    List<Booking> getItemNearestBooking(Integer itemId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id  = ?2 " +
            "and b.bookedBy.id = ?1 " +
            "and b.status in ('PAST', 'CURRENT', 'APPROVED') " +
            "and b.from < now()")
    List<Booking> getUserItemBooking(Integer userId, Integer itemId);
}
