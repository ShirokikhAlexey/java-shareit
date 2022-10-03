package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;

public interface BookingService {
    BookingDto create(BookingDto bookingDto) throws NotFoundException;

    BookingDto update(Integer bookingId, BookingDto bookingDto) throws NotFoundException, InvalidUserException;

    BookingDto get(Integer bookingId) throws NotFoundException;

    BookingDto changeStatus(int bookingId, boolean newStatus, int userId) throws NotFoundException, InvalidUserException;

    BookingDto get(Integer bookingId, Integer userId) throws NotFoundException, InvalidUserException;
}
