package com.example.j_booking.dto.request;

import com.example.j_booking.validation.annotations.HotelBookingRequestValidationAnnotation;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.time.LocalDate;

@HotelBookingRequestValidationAnnotation
@Builder
public record HotelBookingRequest(
    @NotNull(message = "room must not be empty")
    @PositiveOrZero(message = "roomId must be positive or zero")
    Long roomId,

    @NotNull(message = "checkIn must not be empty")
    @Future
    LocalDate checkIn,

    @NotNull(message = "checkOut must not be empty")
    @Future
    LocalDate checkOut
) {}
