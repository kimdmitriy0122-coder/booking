package com.example.j_booking.repository;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("""
        SELECT r FROM Room r 
        WHERE NOT EXISTS (
            SELECT b FROM BookingRecord b 
            WHERE b.room = r 
            AND b.status <> 'CANCELLED'
            AND b.checkIn < :#{#req.checkOut} 
            AND b.checkOut > :#{#req.checkIn}
        )
    """)
    Page<Room> findAvailableRooms(@Param("req") HotelBookingRequest req, Pageable pageable);


}
