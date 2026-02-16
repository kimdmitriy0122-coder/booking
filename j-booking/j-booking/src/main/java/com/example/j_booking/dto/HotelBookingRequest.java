package com.example.j_booking.dto;

import com.example.j_booking.constants.City;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record HotelBookingRequest(
    Room room,
    LocalDate checkIn,
    LocalDate checkOut
) {}
