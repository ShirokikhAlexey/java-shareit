package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.util.Status;
import ru.practicum.shareit.db.db.BookingRepository;
import ru.practicum.shareit.db.db.ItemRepository;
import ru.practicum.shareit.db.db.UserRepository;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

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
        Optional<User> user = userRepository.findById(bookingDto.getUser_id());
        Optional<Item> item = itemRepository.findById(bookingDto.getItem_id());
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        if (item.isEmpty()) {
            throw new NotFoundException();
        }

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
        if (newStatus) {
            newStatusValue = Status.APPROVED;
        } else {
            newStatusValue = Status.REJECTED;
        }
        if(booking.get().getItem().getOwner().getId() != userId){
            throw new InvalidUserException();
        }

        BookingDto bookingDto = new BookingDto(booking.get().getBookedBy().getId(),
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
                !Objects.equals(booking.get().getItem().getOwner().getId(), userId)){
            throw new InvalidUserException();
        }
        return BookingMapper.toDto(booking.get());
    }
}
