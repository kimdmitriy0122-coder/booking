package com.example.j_booking.dto;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.Room;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


public record HotelBookingResponse(
    Room room,
    LocalDate checkIn,
    LocalDate checkOut,
    BookingStatus status
) {
    public static HotelBookingResponse getResponse(Room room, LocalDate checkIn, LocalDate checkOut, BookingStatus status) {
        return new HotelBookingResponse(room, checkIn, checkOut, status);
    }
}
