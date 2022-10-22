package ru.practicum.shareit.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Integer> {
    @Query(value = "select it " +
            "from ItemRequest as it " +
            "where it.author.id = ?1 ")
    List<ItemRequest> getUserRequests(Integer userId);
}