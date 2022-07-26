package ru.practicum.shareit.db.base;

import ru.practicum.shareit.user.User;

import java.util.List;


public interface UserStorageBase {
    public User get(Integer id);

    public User create(User user);

    public User update(User user);

    public void delete(Integer id);

    public User findByEmail(String email);

    public List<User> getAll();
}
