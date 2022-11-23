package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingDto bookingDto) throws NotFoundException;

    BookingDto update(Integer bookingId, BookingDto bookingDto) throws NotFoundException;

    BookingDto get(Integer bookingId) throws NotFoundException;

    BookingDto changeStatus(int bookingId, boolean newStatus, int userId) throws NotFoundException;

    BookingDto get(Integer bookingId, Integer userId) throws NotFoundException;

    List<BookingDto> getStatusList(Integer userId, String state, Integer from, Integer size);

    List<BookingDto> getUserItemsBookings(Integer userId, String state, Integer from, Integer size);
}
