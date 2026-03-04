package com.example.j_booking.service;

import com.example.j_booking.dto.BookingRecordDto;
import com.example.j_booking.dto.PaymentDto;
import com.example.j_booking.dto.request.BookingRecordListRequest;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.request.PageableRequestWithDates;
import com.example.j_booking.dto.response.BookingRecordListResponse;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.HotelDto;
import com.example.j_booking.dto.response.HotelListResponse;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.response.PaymentResponse;
import com.example.j_booking.dto.response.RoomListResponse;
import com.example.j_booking.entity.Room;

public interface HotelBookingService {
    Room getRoomById(Long id);
//    BookingRecord getBookingRecordByRequest(HotelBookingRequest request);
//    HotelBookingResponse bookHotelRoomByRecord(BookingRecord record);
    HotelBookingResponse checkRoomAvailabilityWithDates(HotelBookingRequest request);
//    HotelBookingResponse checkRoomAvailability(HotelBookingRequest request);
//    BookingStatus checkStatusByRecord(BookingRecord record);
//    Page<Room> getAvailableRoomList(PageableRequestWithDates request);
    RoomListResponse<RoomDto> getAvailableRoomList(PageableRequestWithDates request);
    HotelListResponse<HotelDto> getAvailableHotelList(PageableRequestWithDates request);
    HotelBookingResponse bookHotelRoomByRequest(HotelBookingRequest request);
    BookingRecordListResponse getBookingRecordList(BookingRecordListRequest bookingRecordListRequest, PageableRequest pageableRequest);
    BookingRecordDto confirmBooking(PaymentDto dto);
}
