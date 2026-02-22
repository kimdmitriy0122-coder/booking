package com.example.j_booking.service;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;

public interface HotelBookingService {
    Room getRoomById(Long id);
    BookingRecord getBookingRecordByRequest(HotelBookingRequest request);
    HotelBookingResponse bookHotelRoomWithDates(BookingRecord record);
    HotelBookingResponse checkRoomAvailabilityWithDates(HotelBookingRequest request);
}
