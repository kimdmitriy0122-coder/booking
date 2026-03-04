package com.example.j_booking.dto.request;

import com.example.j_booking.constants.Currency;
import com.example.j_booking.constants.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentRequest(
        @NotNull(message = "referenceId required")
        UUID referenceId,
        @NotNull(message = "transaction type required")
        TransactionType type,
        @NotNull(message = "amount is required")
        @Positive(message = "amount should be positive")
        Long amount,
        @NotNull(message = "currency required")
        Currency currency,
        @NotNull(message = "merchant required")
        UUID merchantId,
        @NotBlank(message = "senderName required")
        String senderName,
        @NotBlank(message = "senderToken required")
        String senderToken,
        @NotBlank(message = "receiverName required")
        String receiverName,
        @NotBlank(message = "receiverToken required")
        String receiverToken
) {
}
