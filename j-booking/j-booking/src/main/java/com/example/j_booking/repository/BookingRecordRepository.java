package com.example.j_booking.repository;

import com.example.j_booking.entity.BookingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRecordRepository extends JpaRepository<BookingRecord, Long> {

//    public HotelBookingResponse bookHotelWithDate(Long roomId, LocalDate checkIn, LocalDate checkOut);
}
