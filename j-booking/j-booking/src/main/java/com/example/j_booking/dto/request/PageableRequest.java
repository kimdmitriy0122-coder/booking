package com.example.j_booking.dto.request;

import com.example.j_booking.validation.annotations.PageableRequestValidationAnnotation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@PageableRequestValidationAnnotation
public record PageableRequest(
    @NotNull(message = "page must not be empty")
    @PositiveOrZero(message = "page must be positive or zero")
    int page,
    @NotNull(message = "size must not be empty")
    @PositiveOrZero(message = "size must be positive or zero")
    int size,
    @NotNull(message = "checkIn must not be empty")
    LocalDate checkIn,
    @NotNull(message = "checkOut must not be empty")
    LocalDate checkOut
) {}
