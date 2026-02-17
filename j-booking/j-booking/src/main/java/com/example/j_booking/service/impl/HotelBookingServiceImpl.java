package com.example.j_booking.service.impl;

import com.example.j_booking.constants.RoomStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelBookingServiceImpl implements HotelBookingService {
    private final BookingRecordRepository bookingRecordRepository;
    private final RoomRepository roomRepository;
    private final HotelBookingMapper mapper;

    @Override
    public Room getRoomById(HotelBookingRequest request) {
        return null;
    }
    @Transactional
    @Override
    public HotelBookingResponse bookHotelRoomWithDates(HotelBookingRequest request) {

        BookingRecord record = mapper.toEntity(request);
        record.setRoom(roomRepository.getReferenceById(request.roomId()));

        BookingRecord saved = bookingRecordRepository.save(record);

        return new HotelBookingResponse(
            saved.getRoom(),
            saved.getCheckIn(),
            saved.getCheckOut(),
            RoomStatus.BOOKED
        );
    }
}
