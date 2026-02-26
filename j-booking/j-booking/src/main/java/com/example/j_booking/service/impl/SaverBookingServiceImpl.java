package com.example.j_booking.service.impl;

import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.service.SaverBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaverBookingServiceImpl implements SaverBookingService {
    BookingRecordRepository bookingRecordRepository;

    @Override
    @Transactional
    public BookingRecord saveBookingRecord(BookingRecord record){
        return bookingRecordRepository.save(record);
    }
}
