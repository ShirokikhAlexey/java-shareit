package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    public ItemServiceImpl(UserRepository userRepository, ItemRepository itemRepository,
                           BookingRepository bookingRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ItemDto create(ItemDto item, Integer userId) throws NotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        validate(item);
        return ItemMapper.toDto(itemRepository.save(ItemMapper.fromDto(user.get(), item)));
    }

    @Override
    public ItemDto update(Integer itemId, ItemDto itemDto, Integer userId) throws NotFoundException, InvalidUserException {
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
    public ItemDto get(Integer itemId) throws NotFoundException {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException();
        }
        ItemDto itemDto = ItemMapper.toDto(item.get());
        itemDto.setComments(getItemComments(itemId));
        return itemDto;
    }

    @Override
    public List<ItemDto> getAll(Integer userId) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemRepository.findByOwner_Id(userId)) {
            LocalDateTime latestBookingTime = bookingRepository.getItemLatestBooking(item.getId());
            LocalDateTime nearestBookingTime = bookingRepository.getItemNearestBooking(item.getId());
            ItemDto itemDto = ItemMapper.toDto(item);

            itemDto.setLatestBooking(latestBookingTime);
            itemDto.setNearestBooking(nearestBookingTime);

            itemDto.setComments(getItemComments(item.getId()));
            result.add(itemDto);
        }
        return result;
    }

    @Override
    public List<ItemDto> search(String text) {
        List<ItemDto> result = new ArrayList<>();
        if (text.isEmpty()) {
            return result;
        }
        for (Item item : itemRepository.search(text)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }

    @Override
    public void addComment(CommentDto commentDto) throws NotFoundException, InvalidUserException {
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

        System.out.println(bookingRepository.getUserItemBooking(commentDto.getUserId(), commentDto.getItemId()));
        if (bookingRepository.getUserItemBooking(commentDto.getUserId(), commentDto.getItemId()) == null) {
            throw new InvalidUserException();
        }

        Comment comment = new Comment(user, item, commentDto.getReview());

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getItemComments(Integer itemId) {
        List<Comment> comments = commentRepository.findByItem_Id(itemId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(new CommentDto(comment.getAuthor().getId(), comment.getItem().getId(),
                    comment.getReview()));
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
