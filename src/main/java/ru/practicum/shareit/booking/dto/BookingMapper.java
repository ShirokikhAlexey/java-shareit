package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(booking.getBookedBy().getId(), booking.getItem().getId(), booking.getFrom(),
                booking.getTo(), booking.getStatus(), booking.getReview());
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
