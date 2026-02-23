package com.example.j_booking.controller;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.service.HotelBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/booking/")
@RequiredArgsConstructor
public class HotelBookingController {
    private final HotelBookingService service;

    @GetMapping("/isRoomAvailableForDates")
    public ResponseEntity<HotelBookingResponse> getRoomAvailability(@Valid HotelBookingRequest request){
        HotelBookingResponse response = service.checkRoomAvailabilityWithDates(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAvailableRoomList")
    public Page<Room> getAvailableRoomList(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut){
        return service.getAvailableRoomList(checkIn, checkOut, page, size);
    }
    @PostMapping("/bookRoomWithDates")
    public ResponseEntity<HotelBookingResponse> bookRoomWithDates(@Valid @RequestBody HotelBookingRequest request) {
        BookingRecord record = service.getBookingRecordByRequest(request);
        return ResponseEntity.ok(service.bookHotelRoomWithDates(record));
    }
}
