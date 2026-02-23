package com.example.j_booking.controller;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
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
        BookingRecord record = service.getBookingRecordByRequest(request);
        return ResponseEntity
                .ok()
                .body(HotelBookingResponse
                        .getResponse(
                                service.getRoomById(request.roomId()),
                                request.checkIn(),
                                request.checkOut(),
                                service.checkStatusByRecord(record)
                        ));
    }
    @GetMapping("/getAvailableRoomList")
    public Page<Room> getAvailableRoomList(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut){
        return service.getAvailableRoomList(checkIn, checkOut, page, size);
    }
    @PostMapping("/bookRoomWithDates")
    public ResponseEntity<HotelBookingResponse> bookRoomWithDates(@Valid @RequestBody HotelBookingRequest request) {
        BookingRecord record = service.getBookingRecordByRequest(request);
        BookingStatus status = service.checkStatusByRecord(record);
        return ResponseEntity.ok(service.bookHotelRoomWithDates(record));
    }
    @GetMapping("/getAvailableHotelList")
    public Page<Hotel> getAvailableHotelList(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam LocalDate checkIn, @RequestParam LocalDate checkOut){
        return service.getAvailableHotelList(checkIn, checkOut, page, size);
    }
}
