package ru.practicum.shareit.db.memory;

import ru.practicum.shareit.db.base.UserStorageBase;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.practicum.shareit.ShareItApp.itemStorage;

public class UserStorageMemory implements UserStorageBase {
    HashMap<Integer, User> userList = new HashMap<>();
    int counter = 0;

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
        for(User user : userList.values()){
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
