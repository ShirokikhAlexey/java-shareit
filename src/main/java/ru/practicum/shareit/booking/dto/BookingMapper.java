package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getBookedBy().getId(), booking.getItem().getId(), booking.getFrom(),
                booking.getTo(), booking.getStatus(), booking.getReview(),
                UserMapper.toDto(booking.getBookedBy()), ItemMapper.toDto(booking.getItem()));
    }

    public static Booking fromDto(User user, Item item, BookingDto bookingDto) {
        return new Booking(item, user, bookingDto.getFrom(), bookingDto.getTo(), bookingDto.getStatus(),
                bookingDto.getReview());
    }

    public static Booking updateFromDto(Booking booking, BookingDto bookingDto) {
        if (bookingDto.getReview() != null) {
            booking.setReview(bookingDto.getReview());
        }
        if (bookingDto.getStatus() != null) {
            booking.setStatus(bookingDto.getStatus());
        }
        if (bookingDto.getFrom() != null) {
            booking.setFrom(bookingDto.getFrom());
        }
        if (bookingDto.getTo() != null) {
            booking.setTo(bookingDto.getTo());
        }
        return booking;
    }
}
