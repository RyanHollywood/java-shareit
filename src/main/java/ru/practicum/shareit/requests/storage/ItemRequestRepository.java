package ru.practicum.shareit.requests.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.List;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByRequesterId(long requesterId);
}