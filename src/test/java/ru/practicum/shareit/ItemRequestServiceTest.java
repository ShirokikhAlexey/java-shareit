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
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
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
        Assertions.assertEquals(itemRequestDto, created);
    }

}
