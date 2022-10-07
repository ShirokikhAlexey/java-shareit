package ru.practicum.shareit.db.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>, ItemRepositoryCustom {
    List<Item> findByOwner_Id(Integer userId);

    @Query("select it.id " +
            "from Item as it " +
            "where (lower(it.name) like lower('%?1%') or lower(it.description) like lower('%?1%')) " +
            "and it.available = true")
    List<Integer> searchIds(String text);
}
