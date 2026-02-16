package com.example.j_booking.service.impl;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.HotelBookingRepository;
import com.example.j_booking.service.HotelBookingService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HotelBookingServiceImpl implements HotelBookingService {
    private final HotelBookingRepository repository;
    private final HotelBookingMapper mapper;

    @Override
    public ResponseEntity<HotelBookingResponse> bookHotelRoomWithDates(HotelBookingRequest request) {
        BookingRecord record = mapper.toEntity(request);
        HotelBookingResponse response = repository.bookHotelWithDate(
            record.getRoom(),
            record.getCheckIn(),
            record.getCheckOut()
        );
        return ResponseEntity.ok(response);
//        return null;
    }
}
