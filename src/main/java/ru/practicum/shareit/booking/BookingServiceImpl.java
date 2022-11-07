package ru.practicum.shareit.booking;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.exception.InvalidItemException;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(UserRepository userRepository, ItemRepository itemRepository,
                              BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingDto create(BookingDto bookingDto) throws NotFoundException {
        Optional<User> user = userRepository.findById(bookingDto.getBookerId());
        Optional<Item> item = itemRepository.findById(bookingDto.getItemId());

        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        if (item.isEmpty()) {
            throw new NotFoundException();
        }

        if (Objects.equals(item.get().getOwner().getId(), user.get().getId())) {
            throw new NotFoundException();
        }
        validateNew(BookingMapper.fromDto(user.get(), item.get(), bookingDto));

        return BookingMapper.toDto(bookingRepository.save(BookingMapper.fromDto(user.get(), item.get(), bookingDto)));
    }

    @Override
    public BookingDto update(Integer bookingId, BookingDto bookingDto) throws NotFoundException, InvalidUserException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new NotFoundException();
        }

        Booking updatedBooking = BookingMapper.updateFromDto(booking.get(), bookingDto);
        bookingRepository.save(updatedBooking);
        return BookingMapper.toDto(updatedBooking);
    }

    @Override
    public BookingDto get(Integer bookingId) throws NotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new NotFoundException();
        }
        return BookingMapper.toDto(booking.get());
    }

    @Override
    public BookingDto changeStatus(int bookingId, boolean newStatus, int userId) throws NotFoundException,
            InvalidUserException {
        Status newStatusValue;
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new NotFoundException();
        }
        if (booking.get().getStatus() == Status.APPROVED) {
            throw new ValidationException();
        }

        if (newStatus) {
            newStatusValue = Status.APPROVED;
        } else {
            newStatusValue = Status.REJECTED;
        }
        if (booking.get().getItem().getOwner().getId() != userId) {
            throw new NotFoundException();
        }

        BookingDto bookingDto = new BookingDto(booking.get().getId(), booking.get().getBookedBy().getId(),
                booking.get().getItem().getId(), booking.get().getFrom(), booking.get().getTo(), newStatusValue,
                booking.get().getReview());
        Booking updatedBooking = BookingMapper.updateFromDto(booking.get(), bookingDto);
        bookingRepository.save(updatedBooking);
        return BookingMapper.toDto(updatedBooking);
    }

    @Override
    public BookingDto get(Integer bookingId, Integer userId) throws NotFoundException, InvalidUserException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isEmpty()) {
            throw new NotFoundException();
        }
        if (!Objects.equals(booking.get().getBookedBy().getId(), userId) &&
                !Objects.equals(booking.get().getItem().getOwner().getId(), userId)) {
            throw new NotFoundException();
        }
        return BookingMapper.toDto(booking.get());
    }

    @Override
    public List<BookingDto> getStatusList(Integer userId, String state, Integer from, Integer size) {
        if (from <= 0) {
            throw new ValidationException();
        }
        List<Booking> bookings;
        switch (state) {
            case "PAST":
                bookings = bookingRepository.getPast(userId, PageRequest.of(from / size, size));
                break;
            case "CURRENT":
                bookings = bookingRepository.getCurrent(userId, PageRequest.of(from / size, size));
                break;
            case "FUTURE":
                bookings = bookingRepository.getFuture(userId, PageRequest.of(from / size, size));
                break;
            case "WAITING":
            case "REJECTED":
            case "APPROVED":
            case "ALL":
                bookings = bookingRepository.getByStatus(userId, Status.valueOf(state),
                        PageRequest.of(from / size, size));
                break;
            default:
                throw new ValidationException("Unknown state: " + state);
        }

        if (bookings.isEmpty()) {
            throw new NotFoundException();
        }
        List<BookingDto> response = new ArrayList<>();
        for (Booking booking : bookings) {
            response.add(BookingMapper.toDto(booking));
        }
        return response;
    }

    @Override
    public List<BookingDto> getUserItemsBookings(Integer userId, String state, Integer from, Integer size) {
        if (from <= 0) {
            throw new ValidationException();
        }
        List<Booking> bookings;
        switch (state) {
            case "PAST":
                bookings = bookingRepository.getOwnerPast(userId, PageRequest.of(from / size, size));
                break;
            case "CURRENT":
                bookings = bookingRepository.getOwnerCurrent(userId, PageRequest.of(from / size, size));
                break;
            case "FUTURE":
                bookings = bookingRepository.getOwnerFuture(userId, PageRequest.of(from / size, size));
                break;
            case "WAITING":
            case "REJECTED":
            case "APPROVED":
            case "ALL":
                bookings = bookingRepository.getUserItemsBookings(userId, Status.valueOf(state),
                        PageRequest.of(from / size, size));
                break;
            default:
                throw new ValidationException("Unknown state: " + state);
        }
        if (bookings.isEmpty()) {
            throw new NotFoundException();
        }
        List<BookingDto> response = new ArrayList<>();
        for (Booking booking : bookings) {
            response.add(BookingMapper.toDto(booking));
        }
        return response;
    }

    private void validateNew(Booking booking) {
        if (booking.getFrom().isBefore(LocalDateTime.now())) {
            throw new ValidationException();
        }

        if (booking.getTo().isBefore(LocalDateTime.now())) {
            throw new ValidationException();
        }

        if (booking.getTo().isBefore(booking.getFrom())) {
            throw new ValidationException();
        }

        if (!booking.getItem().getAvailable()) {
            throw new InvalidItemException();
        }
    }
}
