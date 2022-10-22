package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "select it " +
            "from Item as it " +
            "where it.owner.id = ?1 " +
            "limit ?3 " +
            "offset ?2 ")
    List<Item> findByOwner_Id(Integer userId, Integer from, Integer size);

    @Query(value = "select it " +
            "from Item as it " +
            "where (lower(it.name) like CONCAT('%',lower(?1),'%') or " +
            "lower(it.description) like CONCAT('%',lower(?1),'%')) " +
            "and it.available = true " +
            "limit ?3 " +
            "offset ?2 ")
    List<Item> search(String text, Integer from, Integer size);
}
