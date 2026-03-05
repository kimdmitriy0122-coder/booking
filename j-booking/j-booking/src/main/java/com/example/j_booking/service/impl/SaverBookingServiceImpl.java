package com.example.j_booking.service.impl;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.service.SaverBookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SaverBookingServiceImpl implements SaverBookingService {
    BookingRecordRepository bookingRecordRepository;

    @Override
    @Transactional
    public BookingRecord saveBookingRecord(BookingRecord record, BookingStatus status){
        BookingRecord result = bookingRecordRepository.save(record);
        result.setStatus(status);
        return result;
    }

    @Override
    @Transactional
    public void updateStatus(BookingRecord record){
        bookingRecordRepository.save(record);
    }
}
