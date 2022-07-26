package ru.practicum.shareit.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "select it " +
            "from Item as it " +
            "where it.owner.id = ?1 ")
    List<Item> findByOwner_Id(Integer userId, Pageable pageable);

    @Query(value = "select it " +
            "from Item as it " +
            "where (lower(it.name) like CONCAT('%',lower(?1),'%') or " +
            "lower(it.description) like CONCAT('%',lower(?1),'%')) " +
            "and it.available = true ")
    List<Item> search(String text, Pageable pageable);
}
