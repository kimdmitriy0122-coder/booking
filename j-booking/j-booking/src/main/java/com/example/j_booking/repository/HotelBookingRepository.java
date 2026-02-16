package com.example.j_booking.repository;

import com.example.j_booking.constants.City;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface HotelBookingRepository extends JpaRepository<Hotel, Long> {
    @Query("INSERT INTO BookingRecord( room, checkIn, checkOut) " +
        "VALUES( :roomId, :checkIn, :checkOut)")
    public HotelBookingResponse bookHotelWithDate(Room room, LocalDate checkIn, LocalDate checkOut);
}
