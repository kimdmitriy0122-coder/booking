package com.example.j_booking.dto.response;


import lombok.Builder;

import java.util.List;

@Builder
public record BookingRecordListResponse<T>(
    List<T> bookingRecordDtoList
) {}
