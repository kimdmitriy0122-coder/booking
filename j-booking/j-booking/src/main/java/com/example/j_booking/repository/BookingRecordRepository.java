package com.example.j_booking.repository;

import com.example.j_booking.entity.BookingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BookingRecordRepository extends JpaRepository<BookingRecord, Long> {

    @Query("SELECT count(b) > 0 " +
            "FROM BookingRecord b " +
            "WHERE b.room = :#{#rec.getRoom()} " +
            "AND b.checkIn < :#{#rec.getCheckOut()} " +
            "AND b.checkOut > :#{#rec.getCheckIn()}")
    boolean isRoomFreeByDates(@Param("rec") BookingRecord record);

    Page<BookingRecord> getBookingRecordsByClient_Id(Long clientId, Pageable pageable);
}
