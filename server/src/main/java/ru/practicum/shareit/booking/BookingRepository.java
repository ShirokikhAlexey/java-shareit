package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
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
            "order by b.from desc ")
    List<Booking> getByStatus(Integer userId, Status state, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.from > CURRENT_TIMESTAMP " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getFuture(Integer userId, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.to <= CURRENT_TIMESTAMP " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getPast(Integer userId, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.from < CURRENT_TIMESTAMP and b.to > CURRENT_TIMESTAMP " +
            "and b.bookedBy.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getCurrent(Integer userId, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.from > CURRENT_TIMESTAMP " +
            "and i.owner.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getOwnerFuture(Integer userId, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.to <= CURRENT_TIMESTAMP " +
            "and i.owner.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getOwnerPast(Integer userId, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where b.from < CURRENT_TIMESTAMP and b.to > CURRENT_TIMESTAMP " +
            "and i.owner.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getOwnerCurrent(Integer userId, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "join Item as i on i.id = b.item.id " +
            "where (b.status = ?2 or ?2 = 'ALL') " +
            "and i.owner.id = ?1 " +
            "order by b.from desc ")
    List<Booking> getUserItemsBookings(Integer userId, Status state, Pageable pageable);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id = ?1 " +
            "and (b.to <= CURRENT_TIMESTAMP or (b.from < CURRENT_TIMESTAMP and b.to > CURRENT_TIMESTAMP)) " +
            "order by b.to desc ")
    List<Booking> getItemLatestBooking(Integer itemId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id = ?1 " +
            "and b.from > CURRENT_TIMESTAMP " +
            "order by b.from asc ")
    List<Booking> getItemNearestBooking(Integer itemId);

    @Query(value = "select b " +
            "from Booking as b " +
            "where b.item.id  = ?2 " +
            "and b.bookedBy.id = ?1 " +
            "and b.status in ('PAST', 'CURRENT', 'APPROVED') " +
            "and b.from < CURRENT_TIMESTAMP")
    List<Booking> getUserItemBooking(Integer userId, Integer itemId);
}
