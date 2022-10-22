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
        Optional<ItemRequest> request = itemRequestRepository.findById(itemRequestId);
        if (request.isEmpty()) {
            throw new NotFoundException();
        }
        return ItemRequestDtoMapper.toDto(request.get());
    }

    @Override
    public List<ItemRequestDto> getUserAll(Integer userId) {
        List<ItemRequestDto> response = new ArrayList<>();
        List<ItemRequest> userRequests = itemRequestRepository.getUserRequests(userId);
        for (ItemRequest request : userRequests) {
            response.add(ItemRequestDtoMapper.toDto(request));
        }
        return response;
    }

    @Override
    public List<ItemRequestDto> getAll(Integer from, Integer size) {
        List<ItemRequestDto> response = new ArrayList<>();
        List<ItemRequest> userRequests = itemRequestRepository.getAllRequests(from, size);
        for (ItemRequest request : userRequests) {
            response.add(ItemRequestDtoMapper.toDto(request));
        }
        return response;
    }
}
