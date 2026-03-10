package com.example.j_booking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record BookingRecordListRequest(
    @Positive
    @NotNull
    Long client_id
) {
}
