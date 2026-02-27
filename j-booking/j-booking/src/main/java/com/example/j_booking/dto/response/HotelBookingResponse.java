package com.example.j_booking.dto.response;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.Room;
import lombok.Getter;

import java.time.LocalDate;


public record HotelBookingResponse(
    Room room,
    LocalDate checkIn,
    LocalDate checkOut,
    BookingStatus status,
    @Getter
    String message
) {
//    public static HotelBookingResponse getResponseWithMessage(Room room, LocalDate checkIn, LocalDate checkOut, BookingStatus status, String message) {
//        return new HotelBookingResponse(room, checkIn, checkOut, status, message);
//    }
//    public static HotelBookingResponse getResponseWithoutMessage(Room room, LocalDate checkIn, LocalDate checkOut, BookingStatus status) {
//        return new HotelBookingResponse(room, checkIn, checkOut, status, null);
//    }

}
