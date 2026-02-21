package com.example.j_booking.dto;


import com.example.j_booking.validation.annotations.CheckoutDateValidationAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@CheckoutDateValidationAnnotation
public record HotelBookingRequest(
    @NotNull(message = "room must not be empty")
    @PositiveOrZero(message = "roomId must be positive or zero")
    Long roomId,

    @NotNull(message = "checkIn must not be empty")
    LocalDate checkIn,

    @NotNull(message = "checkOut must not be empty")
    LocalDate checkOut
) {}
