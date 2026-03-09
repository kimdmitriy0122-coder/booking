package com.example.j_booking.dto.response;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record RoomListResponse<RoomDto>(
    List<RoomDto> content,
    int page,
    int size,
    long totalElements,
    int totalPages
) implements Serializable {}
