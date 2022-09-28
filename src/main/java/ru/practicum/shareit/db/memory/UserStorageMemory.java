package ru.practicum.shareit.db.memory;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.db.base.UserStorageBase;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class UserStorageMemory implements UserStorageBase {
    private final HashMap<Integer, User> userList = new HashMap<>();
    private int counter = 0;

    @Override
    public User get(Integer id) {
        return userList.get(id);
    }

    @Override
    public User create(User user) {
        ++counter;
        user.setId(counter);
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

    @Override
    public User findByEmail(String email) {
        for (User user : userList.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userList.values());
    }
}
