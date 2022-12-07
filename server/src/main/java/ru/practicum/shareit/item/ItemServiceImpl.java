package ru.practicum.shareit.item;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.InvalidItemException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.requests.ItemRequestRepository;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    private final ItemRequestRepository itemRequestRepository;

    public ItemServiceImpl(UserRepository userRepository, ItemRepository itemRepository,
                           BookingRepository bookingRepository, CommentRepository commentRepository,
                           ItemRequestRepository itemRequestRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
        this.itemRequestRepository = itemRequestRepository;
    }

    @Override
    public ItemDto create(ItemDto item, Integer userId) throws NotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        validate(item);
        Item saved = itemRepository.save(ItemMapper.fromDto(user.get(), item));
        System.out.println(saved);
        if (item.getRequestId() != null) {
            addRequestSuggestion(item.getRequestId(), saved);
        }
        return ItemMapper.toDto(saved, item.getRequestId());
    }

    private void addRequestSuggestion(Integer requestId, Item suggestion) {
        Optional<ItemRequest> request = itemRequestRepository.findById(requestId);
        if (request.isEmpty()) {
            throw new NotFoundException();
        }
        ItemRequest requestObject = request.get();
        if (requestObject.getSuggestions() == null) {
            requestObject.setSuggestions(new ArrayList<>());
            requestObject.getSuggestions().add(suggestion);
        } else {
            requestObject.getSuggestions().add(suggestion);
        }
        itemRequestRepository.save(requestObject);
    }

    @Override
    public ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException();
        }
        if (!item.get().getOwner().getId().equals(userId)) {
            throw new NotFoundException();
        }
        Item updatedItem = ItemMapper.updateFromDto(item.get(), itemDto);
        itemRepository.save(updatedItem);
        return ItemMapper.toDto(updatedItem);
    }

    @Override
    public ItemDto get(Integer itemId, Integer userId) throws NotFoundException {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException();
        }
        ItemDto itemDto = ItemMapper.toDto(item.get());

        BookingDto latestBooking;
        List<Booking> bookingsLatest = bookingRepository.getItemLatestBooking(item.get().getId());

        BookingDto nearestBooking;
        List<Booking> bookingsNearest = bookingRepository.getItemNearestBooking(item.get().getId());

        if (bookingsLatest.isEmpty() || !Objects.equals(item.get().getOwner().getId(), userId)) {
            latestBooking = null;
        } else {
            latestBooking = BookingMapper.toDto(bookingsLatest.get(0));
        }

        if (bookingsNearest.isEmpty() || !Objects.equals(item.get().getOwner().getId(), userId)) {
            nearestBooking = null;
        } else {
            nearestBooking = BookingMapper.toDto(bookingsNearest.get(0));
        }


        itemDto.setLastBooking(latestBooking);
        itemDto.setNextBooking(nearestBooking);
        itemDto.setComments(getItemComments(itemId));
        return itemDto;
    }

    @Override
    public List<ItemDto> getAll(Integer userId, Integer from, Integer size) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemRepository.findByOwner_Id(userId, PageRequest.of(from / size, size))) {
            ItemDto itemDto = ItemMapper.toDto(item);

            BookingDto latestBooking;
            List<Booking> bookingsLatest = bookingRepository.getItemLatestBooking(item.getId());

            BookingDto nearestBooking;
            List<Booking> bookingsNearest = bookingRepository.getItemNearestBooking(item.getId());

            if (bookingsLatest.isEmpty()) {
                latestBooking = null;
            } else {
                System.out.println(bookingsLatest.get(0));
                latestBooking = BookingMapper.toDto(bookingsLatest.get(0));
            }

            if (bookingsNearest.isEmpty()) {
                nearestBooking = null;
            } else {
                System.out.println(bookingsNearest.get(0));
                nearestBooking = BookingMapper.toDto(bookingsNearest.get(0));
            }


            itemDto.setLastBooking(latestBooking);
            itemDto.setNextBooking(nearestBooking);

            itemDto.setComments(getItemComments(item.getId()));
            result.add(itemDto);
        }
        return result;
    }

    @Override
    public List<ItemDto> search(String text, Integer from, Integer size) {
        List<ItemDto> result = new ArrayList<>();
        if (text.isEmpty()) {
            return result;
        }
        for (Item item : itemRepository.search(text, PageRequest.of(from / size, size))) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) throws NotFoundException {
        if (commentDto.getReview().isBlank() || commentDto.getReview() == null) {
            throw new ValidationException();
        }

        User user;
        Item item;
        if (userRepository.findById(commentDto.getUserId()).isPresent()) {
            user = userRepository.findById(commentDto.getUserId()).get();
        } else {
            throw new NotFoundException();
        }
        if (itemRepository.findById(commentDto.getItemId()).isPresent()) {
            item = itemRepository.findById(commentDto.getItemId()).get();
        } else {
            throw new NotFoundException();
        }

        if (bookingRepository.getUserItemBooking(commentDto.getUserId(), commentDto.getItemId()).isEmpty()) {
            throw new InvalidItemException();
        }

        return CommentMapper.toDto(commentRepository.save(CommentMapper.fromDto(user, item, commentDto)));
    }

    @Override
    public List<CommentDto> getItemComments(Integer itemId) {
        List<Comment> comments = commentRepository.findByItem_Id(itemId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(CommentMapper.toDto(comment));
        }
        return commentDtos;
    }

    private void validate(ItemDto item) {
        if (item.getAvailable() == null) {
            throw new ValidationException();
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException();
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException();
        }
    }
}
