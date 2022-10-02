package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.db.db.ItemRepository;
import ru.practicum.shareit.db.db.UserRepository;
import ru.practicum.shareit.db.memory.ItemStorageMemory;
import ru.practicum.shareit.db.memory.UserStorageMemory;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    public ItemServiceImpl(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
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
        return ItemMapper.toDto(item.get());
    }

    @Override
    public List<ItemDto> getAll(Integer userId) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemRepository.findByOwner_Id(userId)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
    }

    @Override
    public List<ItemDto> search(String text) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : itemRepository.search(text)) {
            result.add(ItemMapper.toDto(item));
        }
        return result;
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
