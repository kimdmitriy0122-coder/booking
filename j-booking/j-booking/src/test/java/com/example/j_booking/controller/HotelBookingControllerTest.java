package com.example.j_booking.controller;

import com.example.j_booking.constant.TestConstants;
import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.service.impl.HotelBookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class HotelBookingControllerTest {
    @Mock
    BookingRecordRepository bookingRecordRepository;

    @InjectMocks
    HotelBookingServiceImpl hotelBookingService;

    @Test
    void getRoomAvailability() {

        Room room = Room
                .builder()
                .hotelId(TestConstants.ID)
                .id(TestConstants.ID)
                .roomClass(RoomClass.STANDARD)
                .roomCapacity(RoomCapacity.DOUBLE)
                .build()
        ;

        BookingRecord record1 = BookingRecord
                .builder()
                .room(room)
                .id(TestConstants.ID)
                .checkIn(LocalDate.of(2026, 01, 10))
                .checkOut(LocalDate.of(2026, 01, 11))
                .status(BookingStatus.BOOKED)
                .created(LocalDateTime.now())
                .build();

//        when(hotelBookingService.checkRoomAvailabilityWithDates())
    }

    @Test
    void getAvailableRoomList() {
    }

    @Test
    void bookRoomWithDates() {
    }

    @Test
    void getAvailableHotelList() {
    }
}