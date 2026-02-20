package com.example.j_booking.controller;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.Room;
import com.example.j_booking.service.HotelBookingService;
import jakarta.validation.Valid;
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

//    @GetMapping("/isRoomAvailableForDates")
//    public ResponseEntity<> getRoomAvailability(@Valid @RequestBody HotelBookingRequest request){
//        service.
//        return null;
//    }
    @PostMapping("/bookRoomWithDates")
    public ResponseEntity<HotelBookingResponse> bookRoomWithDates(@Valid @RequestBody HotelBookingRequest request) {
//        Room room = service.getRoomById(request);
        service.getBookingRecordByRequest(request);
        return ResponseEntity.ok(service.bookHotelRoomWithDates(request));
    }
}
