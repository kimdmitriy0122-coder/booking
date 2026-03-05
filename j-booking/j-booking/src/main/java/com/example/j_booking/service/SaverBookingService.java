package com.example.j_booking.service;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.BookingRecord;

public interface SaverBookingService {
    BookingRecord saveBookingRecord(BookingRecord record, BookingStatus status);
    void updateStatus(BookingRecord record);
}
