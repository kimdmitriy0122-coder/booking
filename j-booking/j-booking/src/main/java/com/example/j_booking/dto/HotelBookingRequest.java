package com.example.j_booking.dto;


import com.example.j_booking.validation.annotations.HotelBookingRequestValidationAnnotation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@HotelBookingRequestValidationAnnotation
public record HotelBookingRequest(
    @NotNull(message = "room must not be empty")
    @PositiveOrZero(message = "roomId must be positive or zero")
    Long roomId,

    @NotNull(message = "checkIn must not be empty")
    LocalDate checkIn,

    @NotNull(message = "checkOut must not be empty")
    LocalDate checkOut
) {}
