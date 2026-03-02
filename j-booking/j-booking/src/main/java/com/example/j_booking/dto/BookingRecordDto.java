package com.example.j_booking.dto;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingRecordDto(
    UUID paymentId,
    Room room,
    LocalDate checkIn,
    LocalDate checkOut,
    BookingStatus status,
    LocalDateTime created
) {}
