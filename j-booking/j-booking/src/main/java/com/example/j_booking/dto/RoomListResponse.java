package com.example.j_booking.dto;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;

public record RoomListResponse(
    Room room
//    BookingStatus status
) {
}
