package com.example.j_booking.dto.response;

import com.example.j_booking.constants.Currency;
import com.example.j_booking.constants.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID referenceId,
        TransactionStatus status,
        Long amount,
        Currency currency,
        LocalDateTime createdAt
) {}
