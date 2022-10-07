package ru.practicum.shareit.db.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>, ItemRepositoryCustom {
    List<Item> findByOwner_Id(Integer userId);

    @Query(value = "select it.id " +
            "from items as it " +
            "where (lower(it.name) like CONCAT('%',lower(?1),'%') or " +
            "lower(it.description) like CONCAT('%',lower(?1),'%')) " +
            "and it.available = true", nativeQuery = true)
    List<Integer> searchIds(String text);
}
