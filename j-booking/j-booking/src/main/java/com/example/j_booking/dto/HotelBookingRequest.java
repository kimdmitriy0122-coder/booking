package com.example.j_booking.dto;


import com.example.j_booking.validation.annotations.CheckoutDateValidationAnnotation;

import java.time.LocalDate;

@CheckoutDateValidationAnnotation
public record HotelBookingRequest(
    Long roomId,
    LocalDate checkIn,
    LocalDate checkOut
) {}
