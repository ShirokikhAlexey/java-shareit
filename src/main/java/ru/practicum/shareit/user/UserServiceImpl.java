package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static ru.practicum.shareit.ShareItApp.itemStorage;
import static ru.practicum.shareit.ShareItApp.userStorage;


@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserDto create(UserDto user) throws InvalidUserException, InvalidUserParameters {
        if (user.getEmail() == null || user.getName() == null) {
            throw new InvalidUserParameters();
        }
        validate(user);

        return UserMapper.toDto(userStorage.create(UserMapper.userFromDto(user)));
    }

    @Override
    public UserDto update(UserDto userDto, Integer userId) throws NotFoundException, InvalidUserException {
        User user = userStorage.get(userId);
        if (user == null) {
            throw new NotFoundException();
        }
        validate(userDto);
        User updatedUser = UserMapper.updateFromDto(user, userDto);
        userStorage.update(updatedUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public UserDto get(Integer userId) throws NotFoundException {
        User user = userStorage.get(userId);
        if(user == null){
            throw new NotFoundException();
        }
        return UserMapper.toDto(user);
    }

    @Override
    public void delete(Integer userId) throws NotFoundException {
        userStorage.delete(userId);
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> result = new ArrayList<>();
        for(User user : userStorage.getAll()) {
            result.add(UserMapper.toDto(user));
        }
        return result;
    }

    private void validate(UserDto user) throws ValidationException, InvalidUserException {
        if (user.getEmail() != null) {
            validateEmail(user);
        }
    }

    private void validateEmail(UserDto user) throws ValidationException, InvalidUserException {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!Pattern.compile(regexPattern).matcher(user.getEmail()).matches()){
            throw new ValidationException();
        }
        User sameEmail = userStorage.findByEmail(user.getEmail());
        if(sameEmail != null) {
            throw new InvalidUserException();
        }
    }
}
