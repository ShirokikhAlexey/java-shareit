package ru.practicum.shareit.db.memory;

import ru.practicum.shareit.db.base.UserStorageBase;
import ru.practicum.shareit.user.User;

import java.util.HashMap;

public class UserStorageMemory implements UserStorageBase {
    HashMap<Integer, User> userList = new HashMap<>();
    int counter = 0;

    @Override
    public User get(Integer id) {
        return userList.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(counter + 1);
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        userList.remove(id);
    }
}
