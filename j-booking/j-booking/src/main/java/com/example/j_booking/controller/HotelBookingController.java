package com.example.j_booking.controller;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.service.HotelBookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking/")
@RequiredArgsConstructor
public class HotelBookingController {
    private final HotelBookingService service;

//    @GetMapping("room_available")
//    public ResponseEntity<RoomAvailableResponse> getRoomAvailability(RoomAvailableRequest request){
//        RoomAvailableResponse response = service.getRoomAvailability(request);
//        return ResponseEntity
//            .ok()
//            .body(response);
//    }
    @PostMapping("/bookRoomWithDates")
    public ResponseEntity<HotelBookingResponse> getRoomWithDates(@RequestBody HotelBookingRequest request) {
        return service.bookHotelRoomWithDates(request);
    }
}
