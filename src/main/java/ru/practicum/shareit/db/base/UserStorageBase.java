package ru.practicum.shareit.db.base;

import ru.practicum.shareit.user.model.User;

import java.util.List;


public interface UserStorageBase {
    User get(Integer id);

    User create(User user);

    User update(User user);

    void delete(Integer id);

    User findByEmail(String email);

    List<User> getAll();
}
