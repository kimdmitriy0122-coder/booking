package com.example.j_booking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookingRecordListRequest(
    @Positive
    @NotNull
    Long client_id
) {
}
