package com.example.j_booking.service;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.Room;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface HotelBookingService {
    ResponseEntity<HotelBookingResponse> bookHotelRoomWithDates(HotelBookingRequest request);
}
