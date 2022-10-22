package ru.practicum.shareit.requests;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemRequestDtoMapper;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository) {
        this.itemRequestRepository = itemRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemRequestDto create(ItemRequestDto item, Integer userId) {
        Optional<User> author = userRepository.findById(userId);
        if (author.isEmpty()) {
            throw new NotFoundException();
        }
        item.setAuthor(author.get());
        return ItemRequestDtoMapper.toDto(itemRequestRepository.save(ItemRequestDtoMapper.fromDto(item)));
    }

    @Override
    public ItemRequestDto get(Integer itemRequestId) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getAll(Integer userId) {
        List<ItemRequestDto> response = new ArrayList<>();
        List<ItemRequest> userRequests = itemRequestRepository.getUserRequests(userId);
        for (ItemRequest request : userRequests) {
            response.add(ItemRequestDtoMapper.toDto(request));
        }
        return response;
    }
}
