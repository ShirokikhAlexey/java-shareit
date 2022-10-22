package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.ValidationException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping()
    public BookingDto create(@RequestBody BookingDto bookingDto,
                             @RequestHeader("X-Sharer-User-Id") Integer userId) {
        bookingDto.setBookerId(userId);
        BookingDto newBooking = bookingService.create(bookingDto);
        log.info("Добавлен новый запрос на бронирование вещи {}", newBooking.toString());
        return newBooking;
    }

    @PatchMapping(value = "/{bookingId}")
    public BookingDto update(@PathVariable int bookingId, @RequestParam boolean approved,
                             @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return bookingService.changeStatus(bookingId, approved, userId);
    }

    @GetMapping(value = "/{bookingId}")
    public BookingDto get(@PathVariable int bookingId, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return bookingService.get(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getState(@RequestParam(defaultValue = "ALL", required = false) String state,
                                     @RequestHeader("X-Sharer-User-Id") Integer userId,
                                     @RequestParam(defaultValue = "0", required = false) Integer from,
                                     @RequestParam(defaultValue = "10", required = false) Integer size) {
        if (from < 0 || size < 0) {
            throw new ValidationException();
        }
        return bookingService.getStatusList(userId, state, from, size);
    }

    @GetMapping(value = "/owner")
    public List<BookingDto> getUserItemsBookings(@RequestParam(defaultValue = "ALL", required = false) String state,
                                                 @RequestHeader("X-Sharer-User-Id") Integer userId,
                                                 @RequestParam(defaultValue = "0", required = false) Integer from,
                                                 @RequestParam(defaultValue = "10", required = false) Integer size) {
        if (from < 0 || size < 0) {
            throw new ValidationException();
        }
        return bookingService.getUserItemsBookings(userId, state, from, size);
    }

}
