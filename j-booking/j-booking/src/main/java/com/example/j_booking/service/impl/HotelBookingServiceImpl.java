package com.example.j_booking.service.impl;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.exceptions.NoSuchRoomException;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class HotelBookingServiceImpl implements HotelBookingService {
    private final BookingRecordRepository bookingRecordRepository;
    private final RoomRepository roomRepository;
    private final HotelBookingMapper mapper;

    @Override
    public Room getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchRoomException("No room found with id: " + id));
    }

    @Override
    public HotelBookingResponse checkRoomAvailabilityWithDates(HotelBookingRequest request) {
        BookingRecord record = getBookingRecordByRequest(request);
        BookingStatus status = checkStatusByRecord(record);
        return new HotelBookingResponse(
                record.getRoom(),
                record.getCheckIn(),
                record.getCheckOut(),
                status
        );
    }

    @Override
    @Transactional
    public HotelBookingResponse bookHotelRoomWithDates(BookingRecord record) {
        BookingRecord savedRecord = bookingRecordRepository.save(record);
        return new HotelBookingResponse(
                savedRecord.getRoom(),
                savedRecord.getCheckIn(),
                savedRecord.getCheckOut(),
                BookingStatus.BOOKED
        );
    }

    @Override
    public BookingRecord getBookingRecordByRequest(HotelBookingRequest request) {
        BookingRecord record = mapper.toEntity(request);
        record.setRoom(getRoomById(request.roomId()));
        return record;
    }

    private BookingStatus checkStatusByRecord(BookingRecord record) {
        BookingStatus status = bookingRecordRepository
                .isRoomFreeByDates(record) ?
                BookingStatus.BOOKED :
                BookingStatus.FREE;
        return status;
    }

    public Page<Room> getAvailableRooms(HotelBookingRequest request, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return roomRepository.findAvailableRooms(request, pageable);

    }


}
