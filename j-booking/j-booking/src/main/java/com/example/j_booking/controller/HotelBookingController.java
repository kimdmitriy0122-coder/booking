package com.example.j_booking.controller;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.HotelDto;
import com.example.j_booking.dto.response.HotelListResponse;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.response.RoomListResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Hotel;
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
    public ResponseEntity<RoomListResponse<RoomDto>> getAvailableRoomList(@Valid PageableRequest request){
        return ResponseEntity.ok(service.getAvailableRoomList(request));
    }
    @PostMapping("/bookRoomWithDates")
    public ResponseEntity<HotelBookingResponse> bookRoomWithDates(@Valid @RequestBody HotelBookingRequest request) {
        HotelBookingResponse response = service.bookHotelRoomByRequest(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAvailableHotelList")
    public ResponseEntity<HotelListResponse<HotelDto>> getAvailableHotelList(@Valid PageableRequest request){
        return ResponseEntity.ok(service.getAvailableHotelList(request));
    }
}
