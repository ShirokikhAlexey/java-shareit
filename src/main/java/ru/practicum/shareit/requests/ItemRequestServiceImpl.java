package ru.practicum.shareit.requests;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemRequestDtoMapper;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
        validate(item);
        ItemRequest request = itemRequestRepository.save(ItemRequestDtoMapper.fromDto(item, author.get()));
        System.out.println(ItemRequestDtoMapper.toDto(request));
        return ItemRequestDtoMapper.toDto(request);
    }

    private void validate(ItemRequestDto requestDto) {
        if (requestDto.getDescription() == null) {
            throw new ValidationException();
        }
    }

    @Override
    public ItemRequestDto get(Integer itemRequestId, Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        Optional<ItemRequest> request = itemRequestRepository.findById(itemRequestId);
        if (request.isEmpty()) {
            throw new NotFoundException();
        }
        return ItemRequestDtoMapper.toDto(request.get());
    }

    @Override
    public List<ItemRequestDto> getUserAll(Integer userId) {
        List<ItemRequestDto> response = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        List<ItemRequest> userRequests = itemRequestRepository.getUserRequests(userId);
        for (ItemRequest request : userRequests) {
            response.add(ItemRequestDtoMapper.toDto(request));
        }
        return response;
    }

    @Override
    public List<ItemRequestDto> getAll(Integer userId, Integer from, Integer size) {
        List<ItemRequestDto> response = new ArrayList<>();
        List<ItemRequest> userRequests;
        if (userId != null) {
            userRequests = itemRequestRepository.getAllUsersRequests(userId, PageRequest.of(from / size, size));
        } else {
            userRequests = itemRequestRepository.getAllRequests(PageRequest.of(from / size, size));
        }

        for (ItemRequest request : userRequests) {
            response.add(ItemRequestDtoMapper.toDto(request));
        }
        return response;
    }
}
