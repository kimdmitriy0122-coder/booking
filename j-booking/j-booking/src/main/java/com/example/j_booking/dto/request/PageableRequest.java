package com.example.j_booking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record PageableRequest(
    @NotNull(message = "page must not be empty")
    @PositiveOrZero(message = "page must be positive or zero")
    int page,
    @NotNull(message = "size must not be empty")
    @PositiveOrZero(message = "size must be positive or zero")
    int size
) {}
