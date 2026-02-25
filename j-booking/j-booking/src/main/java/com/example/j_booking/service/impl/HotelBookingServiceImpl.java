package com.example.j_booking.service.impl;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.dto.PageableRequest;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelBookingServiceImpl implements HotelBookingService {
    BookingRecordRepository bookingRecordRepository;
    RoomRepository roomRepository;
    HotelRepository hotelRepository;
    HotelBookingMapper mapper;

    @Override
    @Cacheable(value = "rooms", key = "#id")
    public Room getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchRoomException("No room found with id: " + id));
    }

    @Override
    @Transactional
    //после бронирования комнаты удалить кэш
    @CacheEvict(value = {"availableRooms", "availableHotels"}, allEntries = true)
    public HotelBookingResponse bookHotelRoomByRecord(BookingRecord record) {
        BookingRecord savedRecord = bookingRecordRepository.save(record);
        return new HotelBookingResponse(
                savedRecord.getRoom(),
                savedRecord.getCheckIn(),
                savedRecord.getCheckOut(),
                BookingStatus.BOOKED,
            "room has been booked"
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
        return bookingRecordRepository
            .isRoomFreeByDates(record) ?
            BookingStatus.BOOKED :
            BookingStatus.FREE;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "availableRooms", key = "#request")
    public Page<Room> getAvailableRoomList(PageableRequest request) {
        return roomRepository
            .findAvailableRooms(
                request.checkIn(),
                request.checkOut(),
                mapper.toPageRequest(request));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "availableHotels", key = "#request")
    public Page<Hotel> getAvailableHotelList(PageableRequest request) {
        return hotelRepository
                .findAvailableHotels(
                    request.checkIn(),
                    request.checkOut(),
                    mapper.toPageRequest(request))
                ;
    }
}
