package com.example.j_booking.service.impl;

import com.example.j_booking.entity.Room;
import com.example.j_booking.exceptions.NoSuchRoomException;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.RoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;

    @Override
    @Cacheable(value = "rooms", key = "#id")
    @Transactional(readOnly = true)
    public Room getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchRoomException("No room found with id: " + id));
    }
}
