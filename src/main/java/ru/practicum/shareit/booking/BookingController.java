package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.ValidationException;


@Slf4j
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping()
    public BookingDto create(@RequestBody BookingDto bookingDto) {
        try {
            BookingDto newBooking = bookingService.create(bookingDto);
            log.info("Добавлен новый запрос на бронирование вещи {}", newBooking.toString());
            return newBooking;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping(value = "/{bookingId}")
    public BookingDto update(@PathVariable int bookingId, @RequestParam boolean approved,
                          @RequestHeader("X-Sharer-User-Id") Integer userId) {
        try {
            return bookingService.changeStatus(bookingId, approved, userId);
        } catch (NotFoundException e) {
            log.info("Попытка обновления несуществующей записи: {}", bookingId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InvalidUserException e) {
            log.info("Попытка изменения записи не владельцем: {}", bookingId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{bookingId}")
    public BookingDto get(@PathVariable int bookingId, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        try {
            return bookingService.get(bookingId, userId);
        } catch (NotFoundException e) {
            log.info("Запись не найдена: {}", bookingId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InvalidUserException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
