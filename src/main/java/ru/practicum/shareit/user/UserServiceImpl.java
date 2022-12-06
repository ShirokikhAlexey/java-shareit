package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InvalidUserParameters;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDto create(UserDto user) throws InvalidUserParameters {
        if (user.getEmail() == null || user.getName() == null || user.getName().isBlank() ||
                user.getEmail().isBlank()) {
            throw new InvalidUserParameters();
        }
        validate(user);

        return UserMapper.toDto(repository.save(UserMapper.userFromDto(user)));
    }

    @Override
    public UserDto update(UserDto userDto, Integer userId) throws NotFoundException {
        Optional<User> user = repository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        validate(userDto);
        User updatedUser = UserMapper.updateFromDto(user.get(), userDto);
        repository.save(updatedUser);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public UserDto get(Integer userId) throws NotFoundException {
        Optional<User> user = repository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException();
        }
        return UserMapper.toDto(user.get());
    }

    @Override
    public void delete(Integer userId) throws NotFoundException {
        repository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> result = new ArrayList<>();
        for (User user : repository.findAll()) {
            result.add(UserMapper.toDto(user));
        }
        return result;
    }

    private void validate(UserDto user) throws ValidationException {
        if (user.getEmail() != null) {
            validateEmail(user);
        }
    }

    private void validateEmail(UserDto user) throws ValidationException {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (!Pattern.compile(regexPattern).matcher(user.getEmail()).matches()) {
            throw new ValidationException();
        }
    }
}
