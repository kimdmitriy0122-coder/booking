package com.example.j_booking.service.impl;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.dto.request.BookingRecordListRequest;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.response.BookingRecordListResponse;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.HotelDto;
import com.example.j_booking.dto.response.HotelListResponse;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.response.RoomListResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import com.example.j_booking.exceptions.NoSuchRoomException;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.HotelRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.HotelBookingService;
import com.example.j_booking.service.SaverBookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    SaverBookingService saverBookingService;

    @Override
    @Cacheable(value = "rooms", key = "#id")
    public Room getRoomById(Long id) {
        return roomRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchRoomException("No room found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public HotelBookingResponse checkRoomAvailabilityWithDates(HotelBookingRequest request) {
        BookingRecord record = getBookingRecordByRequest(request);
        BookingStatus status = checkStatusByRecord(record);
        String message;
        if(!status.equals(BookingStatus.FREE)){
            message = "can't book room because it's already booked";
        }
        else{
            message = "room booked";
        }
        return mapper.toHotelBookingResponse(record, message);
    }

    @Override
    public HotelBookingResponse bookHotelRoomByRequest(HotelBookingRequest request) {
        BookingRecord record = getBookingRecordByRequest(request);
        BookingStatus status = checkStatusByRecord(record);
        HotelBookingResponse response;
        if(!(status.equals(BookingStatus.FREE) || status.equals(BookingStatus.CANCELLED))){
            response = mapper.toHotelBookingResponse(
                record,
                "can't book room because it's already booked");
        }
        else{
            response = mapper.toHotelBookingResponse(
                saverBookingService.saveBookingRecord(record, BookingStatus.BOOKED),
                "room booked");
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
        value = "availableRooms",
        key = "'page=' + #page + ':size=' + #size"
    )
    public RoomListResponse<RoomDto> getAvailableRoomList(PageableRequest request) {
        Page<Room> rooms = roomRepository
            .findAvailableRooms(
                request.checkIn(),
                request.checkOut(),
                mapper.toPageRequest(request));
        return mapper.toRoomListResponse(rooms);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
        value = "availableHotels",
        key = "'page=' + #page + ':size=' + #size"
    )
    public HotelListResponse<HotelDto> getAvailableHotelList(PageableRequest request) {
        Page<Hotel> hotels = hotelRepository
            .findAvailableHotels(
                request.checkIn(),
                request.checkOut(),
                mapper.toPageRequest(request));
        return mapper.toHotelListResponse(hotels);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
        value = "clientHistory",
        key = "'page=' + #page + ':size=' + #size"
    )
    public BookingRecordListResponse getBookingRecordList(BookingRecordListRequest request) {
        Page<BookingRecord> records = bookingRecordRepository.getBookingRecordsByClient_Id(request.client_id());
        return mapper.toBookingRecordListResponse(records);
    }

    public BookingRecord getBookingRecordByRequest(HotelBookingRequest request) {
        BookingRecord record = mapper.toBookingRecord(request);
        record.setRoom(getRoomById(request.roomId()));
        return record;
    }

    public BookingStatus checkStatusByRecord(BookingRecord record) {
        return bookingRecordRepository
            .isRoomFreeByDates(record) ?
            BookingStatus.BOOKED :
            BookingStatus.FREE;
    }
}
