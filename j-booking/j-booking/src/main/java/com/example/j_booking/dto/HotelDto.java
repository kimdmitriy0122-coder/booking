package com.example.j_booking.dto;

import com.example.j_booking.constants.BuildingType;
import com.example.j_booking.entity.Address;

public record HotelDto(
    Long id,
    short rating,
    String name,
    Address address,
    BuildingType buildingType
) {
}
