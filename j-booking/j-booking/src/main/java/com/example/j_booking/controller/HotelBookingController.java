package com.example.j_booking.controller;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.dto.PageableRequest;
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
                        .getResponseWithoutMessage(
                                service.getRoomById(request.roomId()),
                                request.checkIn(),
                                request.checkOut(),
                                service.checkStatusByRecord(record)
                        ));
    }
    @GetMapping("/getAvailableRoomList")
    public Page<Room> getAvailableRoomList(@Valid PageableRequest request){
        return service.getAvailableRoomList(request);
    }
    @PostMapping("/bookRoomWithDates")
    public ResponseEntity<HotelBookingResponse> bookRoomWithDates(@Valid @RequestBody HotelBookingRequest request) {
        BookingRecord record = service.getBookingRecordByRequest(request);
        BookingStatus status = service.checkStatusByRecord(record);
        ResponseEntity<HotelBookingResponse> response;
        if(!status.equals(BookingStatus.FREE))
            response = ResponseEntity
                .ok()
                .body(
                    HotelBookingResponse
                        .getResponseWithMessage(
                            record.getRoom(),
                            record.getCheckIn(),
                            record.getCheckOut(),
                            BookingStatus.BOOKED,
                    "couldn'n book room. It's already booked"
                ));
        else{
            response = ResponseEntity
                .ok()
                .body(
                    service.bookHotelRoomByRecord(record)
                );

        }
        return response;
    }
    @GetMapping("/getAvailableHotelList")
    public Page<Hotel> getAvailableHotelList(@Valid PageableRequest request){
        return service.getAvailableHotelList(request);
    }
}
