package com.example.j_booking.service.impl;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.dto.RoomListResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import com.example.j_booking.exceptions.NoSuchRoomException;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.HotelRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.HotelBookingService;
import com.example.j_booking.utils.Paginator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelBookingServiceImpl implements HotelBookingService {
    BookingRecordRepository bookingRecordRepository;
    RoomRepository roomRepository;
    HotelRepository hotelRepository;
    HotelBookingMapper mapper;

    @Override
    public Room getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchRoomException("No room found with id: " + id));
    }

//    @Override
//    public HotelBookingResponse checkRoomAvailabilityWithDates(BookingRecord record) {
//        return new HotelBookingResponse(
//                record.getRoom(),
//                record.getCheckIn(),
//                record.getCheckOut(),
//                status
//        );
//    }

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
    @Transactional(readOnly = true)
    public BookingRecord getBookingRecordByRequest(HotelBookingRequest request) {
        BookingRecord record = mapper.toEntity(request);
        record.setRoom(getRoomById(request.roomId()));
        return record;
    }

    @Transactional(readOnly = true)
    @Override
    public BookingStatus checkStatusByRecord(BookingRecord record) {
        BookingStatus status = bookingRecordRepository
                .isRoomFreeByDates(record) ?
                BookingStatus.BOOKED :
                BookingStatus.FREE;
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> getAvailableRoomList(LocalDate checkIn, LocalDate checkOut, int page, int size) {
        var pageable = Paginator.validate(page, size);

        return roomRepository
            .findAvailableRooms(checkIn, checkOut, pageable)
            ;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hotel> getAvailableHotelList(LocalDate checkIn, LocalDate checkOut, int page, int size) {
        var pageable = Paginator.validate(page, size);

        return hotelRepository
                .findAvailableHotels(checkIn, checkOut, pageable)
                ;
    }
}
