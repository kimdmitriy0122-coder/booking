package com.example.j_booking.repository;

import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface HotelBookingRepository extends JpaRepository<Hotel, Long> {
    public ResponseEntity<HotelBookingResponse> bookHotelWithDate();
}
