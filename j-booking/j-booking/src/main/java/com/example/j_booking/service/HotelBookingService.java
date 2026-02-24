package com.example.j_booking.service;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.dto.PageableRequest;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface HotelBookingService {
    Room getRoomById(Long id);
    BookingRecord getBookingRecordByRequest(HotelBookingRequest request);
    HotelBookingResponse bookHotelRoomByRecord(BookingRecord record);
//    HotelBookingResponse checkRoomAvailabilityWithDates(HotelBookingRequest request);
    BookingStatus checkStatusByRecord(BookingRecord record);
    Page<Room> getAvailableRoomList(PageableRequest request);
    Page<Hotel> getAvailableHotelList(PageableRequest request);

}
