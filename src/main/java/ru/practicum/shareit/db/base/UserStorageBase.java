package ru.practicum.shareit.db.base;

import ru.practicum.shareit.user.User;

public interface UserStorageBase {
    public User get(Integer id);

    public User create(User user);

    public User update(User user);

    public void delete(Integer id);
}
