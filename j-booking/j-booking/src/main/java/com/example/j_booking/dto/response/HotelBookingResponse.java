package com.example.j_booking.dto.response;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.Room;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record HotelBookingResponse(
    Room room,
    LocalDate checkIn,
    LocalDate checkOut,
    BookingStatus status,
    String message
) {}