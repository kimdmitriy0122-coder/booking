package com.example.j_booking.controller;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.impl.HotelBookingServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class HotelBookingControllerTest {
    @Mock
    BookingRecordRepository bookingRecordRepository;
    @Mock
    RoomRepository roomRepository;
    @Mock
    HotelBookingMapper hotelBookingMapper;

    @InjectMocks
    HotelBookingServiceImpl hotelBookingService;

    Room room;
    BookingRecord record1;
    HotelBookingRequest request;
    HotelBookingResponse requiredResponse;

    @BeforeEach
    void setUp() {
         room = Room
                .builder()
                .hotelId(1L)
                .id(1L)
                .roomClass(RoomClass.STANDARD)
                .roomCapacity(RoomCapacity.DOUBLE)
                .build()
                ;

        record1 = BookingRecord
                .builder()
                .room(room)
                .id(1L)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.BOOKED)
                .created(LocalDateTime.now())
                .build();

        request = HotelBookingRequest
                .builder()
                .roomId(1L)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .build();

        requiredResponse = HotelBookingResponse
                .builder()
                .room(room)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.BOOKED)
                .message("can't book room because it's already booked")
                .build();
    }

    @Test
    @DisplayName("checking availability when room already booked")
    void getRoomAvailability() {

        when(hotelBookingMapper.toBookingRecord(request)).thenReturn(record1);
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(hotelBookingService.checkRoomAvailabilityWithDates(request)).thenReturn(requiredResponse);

        HotelBookingResponse actualResponse = hotelBookingService.checkRoomAvailabilityWithDates(request);

        assertThat(actualResponse).isEqualTo(requiredResponse);
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