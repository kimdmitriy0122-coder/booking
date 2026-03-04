package com.example.j_booking.service.impl;

import com.example.j_booking.adapter.TransactionService;
import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.Currency;
import com.example.j_booking.constants.TransactionType;
import com.example.j_booking.dto.BookingRecordDto;
import com.example.j_booking.dto.request.BookingRecordListRequest;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.request.PaymentRequest;
import com.example.j_booking.dto.response.BookingRecordListResponse;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.HotelDto;
import com.example.j_booking.dto.response.HotelListResponse;
import com.example.j_booking.dto.request.PageableRequestWithDates;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.response.PaymentResponse;
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

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HotelBookingServiceImpl implements HotelBookingService {
    BookingRecordRepository bookingRecordRepository;
    RoomRepository roomRepository;
    HotelRepository hotelRepository;
    HotelBookingMapper mapper;
    SaverBookingService saverBookingService;
    TransactionService transactionService;

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
    public RoomListResponse<RoomDto> getAvailableRoomList(PageableRequestWithDates request) {
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
    public HotelListResponse<HotelDto> getAvailableHotelList(PageableRequestWithDates request) {
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
    public BookingRecordListResponse<BookingRecordDto> getBookingRecordList(BookingRecordListRequest bookingRecordListRequest, PageableRequest pageableRequest) {
        Page<BookingRecord> records = bookingRecordRepository
            .getBookingRecordsByClientId(
                bookingRecordListRequest.client_id(),
                mapper.toPageRequest(pageableRequest));
        return mapper.toBookingRecordListResponse(records);
    }

    @Override
    public BookingRecordDto confirmBooking(BookingRecordDto request){
        PaymentRequest paymentRequest = PaymentRequest
            .builder()
            .referenceId(request.paymentId())
            .amount(1000L)
            .type(TransactionType.P2P)
            .currency(Currency.USD)
            .merchantId(UUID.fromString("55a78372-9852-440d-9da9-61dedd572464"))
            .senderName("Hilton")
            .senderToken("string")
            .receiverName("bank")
            .receiverToken("string")
            .build();
        PaymentResponse paymentResponse = transactionService.sendPayment(paymentRequest);


//        return transactionService.sendPayment(paymentRequest);
        return request;
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
