package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.requests.ItemRequestRepository;
import ru.practicum.shareit.requests.ItemRequestService;
import ru.practicum.shareit.requests.ItemRequestServiceImpl;
import ru.practicum.shareit.requests.dto.ItemRequestDto;
import ru.practicum.shareit.requests.dto.ItemRequestDtoMapper;
import ru.practicum.shareit.requests.model.ItemRequest;
import ru.practicum.shareit.requests.util.Status;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ItemRequestServiceTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    ItemRequestRepository itemRequestRepository = Mockito.mock(ItemRequestRepository.class);
    ItemRequestService itemRequestService = new ItemRequestServiceImpl(itemRequestRepository, userRepository);

    @Test
    public void testCreateValidation() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        ItemRequestDto itemRequestDto = new ItemRequestDto();

        Assertions.assertThrows(NotFoundException.class, () -> itemRequestService.create(itemRequestDto, 1));
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new User("test", "test")));

        Assertions.assertThrows(ValidationException.class, () -> itemRequestService.create(itemRequestDto, 1));
        itemRequestDto.setDescription(" ");
        Assertions.assertThrows(ValidationException.class, () -> itemRequestService.create(itemRequestDto, 1));
    }

    @Test
    public void testCreateSuccess() {
        User user = new User(1, "test", "test");
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
        ItemRequestDto itemRequestDto = new ItemRequestDto("test");
        ItemRequest itemRequest = ItemRequestDtoMapper.fromDto(itemRequestDto, user);

        Mockito.when(itemRequestRepository.save(Mockito.any())).thenReturn(itemRequest);

        ItemRequestDto created = itemRequestService.create(itemRequestDto, 1);
        itemRequestDto.setAuthor(UserMapper.toDto(user));
        Assertions.assertEquals(itemRequestDto.getDescription(), created.getDescription());
        Assertions.assertEquals(itemRequestDto.getAuthor(), created.getAuthor());
        Assertions.assertEquals(itemRequestDto.getStatus(), created.getStatus());
    }

    @Test
    public void testGet() {
        User user = new User(1, "test", "test");
        ItemRequest request = new ItemRequest(1, user, "test", Status.OPEN, LocalDateTime.now(), null);

        Mockito.when(userRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> itemRequestService.get(1, 2));

        Mockito.when(itemRequestRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> itemRequestService.get(2, 1));

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRequestRepository.findById(1)).thenReturn(Optional.of(request));
        Assertions.assertEquals(ItemRequestDtoMapper.toDto(request), itemRequestService.get(1, 1));
    }

    @Test
    public void testGetUserAll() {
        User user = new User(1, "test", "test");
        ItemRequest request = new ItemRequest(1, user, "test", Status.OPEN, LocalDateTime.now(), null);

        Mockito.when(userRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> itemRequestService.getUserAll(2));

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(itemRequestRepository.getUserRequests(1)).thenReturn(List.of(request));

        Assertions.assertEquals(List.of(ItemRequestDtoMapper.toDto(request)), itemRequestService.getUserAll(1));
    }

    @Test
    public void testGetAll() {
        User user = new User(1, "test", "test");
        ItemRequest requestUser = new ItemRequest(1, user, "test", Status.OPEN, LocalDateTime.now(), null);
        ItemRequest request = new ItemRequest(2, user, "test", Status.OPEN, LocalDateTime.now(), null);

        Mockito.when(itemRequestRepository.getAllUsersRequests(Mockito.anyInt(), Mockito.any())).thenReturn(List.of(requestUser));
        Mockito.when(itemRequestRepository.getAllRequests(Mockito.any())).thenReturn(List.of(request));

        Assertions.assertEquals(List.of(ItemRequestDtoMapper.toDto(requestUser)), itemRequestService.getAll(1, 1, 1));
        Assertions.assertEquals(List.of(ItemRequestDtoMapper.toDto(request)), itemRequestService.getAll(null, 1, 1));
    }

}
