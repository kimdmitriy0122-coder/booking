package com.example.j_booking.controller;

import com.example.j_booking.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking/")
@RequiredArgsConstructor
public class HotelBookingContoller {
    HotelBookingService service;

    @GetMapping("room_available")
    public ResponseEntity<RoomAvailableResponse> getRoomAvailability(RoomAvailableRequest request){
        RoomAvailableResponse response = service.getRoomAvailability(request);
        return ResponseEntity
            .ok()
            .body(response);
    }
}
