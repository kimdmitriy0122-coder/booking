package com.example.j_booking.dto;

import com.example.j_booking.constants.RoomStatus;
import com.example.j_booking.entity.Room;

import java.time.LocalDate;

public record HotelBookingResponse(
    Room room,
    LocalDate checkIn,
    LocalDate checkOut,
    RoomStatus status
) {}
