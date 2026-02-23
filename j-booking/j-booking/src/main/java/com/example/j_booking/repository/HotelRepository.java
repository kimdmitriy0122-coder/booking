package com.example.j_booking.repository;

import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("""
        SELECT h FROM Hotel h
        WHERE EXISTS (
            SELECT r FROM Room r
            WHERE r.hotelId = h.id
            AND NOT EXISTS (
                SELECT b FROM BookingRecord b
                WHERE b.room = r
                AND b.status <> 'CANCELLED'
                AND b.checkIn < :checkOut
                AND b.checkOut > :checkIn
            )
        )
    """)
    Page<Hotel> findAvailableHotels(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut, Pageable pageable);

}
