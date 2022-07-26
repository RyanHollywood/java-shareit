package ru.practicum.shareit.booking.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBookerIdOrderByStartDesc(long bookerId);

    List<Booking> findByBookerIdOrderByStartDesc(long bookerId, PageRequest pageRequest);

    List<Booking> findAllByItemIdInOrderByStartDesc(List<Long> itemIds);

    List<Booking> findAllByItemIdInOrderByStartDesc(List<Long> itemIds, PageRequest pageRequest);
}
