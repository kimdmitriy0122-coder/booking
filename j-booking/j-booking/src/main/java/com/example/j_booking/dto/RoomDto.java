package com.example.j_booking.dto;

import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import lombok.Builder;

@Builder
public record RoomDto(
    Long id,
    Long hotelId,
    RoomClass roomClass,
    RoomCapacity roomCapacity
) {
}
