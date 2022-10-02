package ru.practicum.shareit.db.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwner_Id(Integer userId);

    @Query(value = "select * "+
            "from items as it "+
            "where (lower(it.name) like lower('%?1%') or lower(it.description) like lower('%?1%')) " +
            "and it.available = true;")
    List<Item> search(String text);
}
