package com.example.j_booking.dto.error;

import com.example.j_booking.constants.exceptions.ExceptionCode;

public record CommonErrorResponse(
    int code,
    String message
) {}
