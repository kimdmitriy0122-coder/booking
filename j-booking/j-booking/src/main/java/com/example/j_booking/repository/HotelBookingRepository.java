package com.example.j_booking.repository;

import com.example.j_booking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelBookingRepository extends JpaRepository<Hotel, Long> {
}
