package com.example.j_booking.controller;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.RoomCapacity;
import com.example.j_booking.constants.RoomClass;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.request.PageableRequestWithDates;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.response.RoomListResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Room;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.impl.HotelBookingServiceImpl;

import com.example.j_booking.utils.Paginator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    Room room1, room2, room3, room4;
    RoomDto roomDto1, roomDto2, roomDto3, roomDto4;
    BookingRecord record1;
    HotelBookingRequest request;
    HotelBookingResponse requiredResponse;
    PageableRequest pageableRequest;
    PageableRequestWithDates pageableRequestWithDates;
    Page<RoomDto> roomDtoPage;
    Page<Room> roomPage;
    RoomListResponse roomListResponse;

    @BeforeEach
    void setUp() {
         room1 = Room
                .builder()
                .hotelId(1L)
                .id(1L)
                .roomClass(RoomClass.STANDARD)
                .roomCapacity(RoomCapacity.DOUBLE)
                .build()
                ;
        room2 = Room
            .builder()
            .hotelId(1L)
            .id(2L)
            .roomClass(RoomClass.LUX)
            .roomCapacity(RoomCapacity.DOUBLE)
            .build()
        ;
        room3 = Room
            .builder()
            .hotelId(1L)
            .id(3L)
            .roomClass(RoomClass.PRESIDENT_LUX)
            .roomCapacity(RoomCapacity.DOUBLE)
            .build()
        ;
        room4 = Room
            .builder()
            .hotelId(1L)
            .id(4L)
            .roomClass(RoomClass.SUPERIOR)
            .roomCapacity(RoomCapacity.DOUBLE)
            .build()
        ;
        roomDto1 = RoomDto
            .builder()
            .id(1L)
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.STANDARD)
            .build();

        roomDto2 = RoomDto
            .builder()
            .id(2L)
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.LUX)
            .build();

        roomDto3 = RoomDto
            .builder()
            .id(3L)
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.PRESIDENT_LUX)
            .build();

        roomDto4 = RoomDto
            .builder()
            .id(4L)
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.SUPERIOR)
            .build();

        record1 = BookingRecord
                .builder()
                .room(room1)
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
                .room(room1)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.BOOKED)
                .message("can't book room because it's already booked")
                .build();

        pageableRequest = PageableRequest
            .builder()
            .page(0)
            .size(5)
            .build();

        pageableRequestWithDates = PageableRequestWithDates
            .builder()
            .checkIn(LocalDate.of(2026, 1, 10))
            .checkOut(LocalDate.of(2026, 1, 11))
            .page(0)
            .size(5)
            .build();

        roomDtoPage = new PageImpl<>(List.of(roomDto1, roomDto2, roomDto3, roomDto4));

        roomPage = new PageImpl<>(List.of(room1, room2, room3, room4));

        roomListResponse = RoomListResponse
            .<RoomDto>builder()
            .content(roomDtoPage.toList())
            .totalElements(4)
            .page(0)
            .size(5)
            .build();


    }

    @Test
    @DisplayName("checking availability when room already booked")
    void getRoomAvailability() {

        when(hotelBookingMapper.toBookingRecord(request)).thenReturn(record1);
        when(roomRepository.findById(room1.getId())).thenReturn(Optional.of(room1));
        when(hotelBookingService.checkRoomAvailabilityWithDates(request)).thenReturn(requiredResponse);

        HotelBookingResponse actualResponse = hotelBookingService.checkRoomAvailabilityWithDates(request);

        assertThat(actualResponse).isEqualTo(requiredResponse);
    }

    @Test
    void getAvailableRoomList() {

        when(hotelBookingMapper.toPageRequest(pageableRequestWithDates))
            .thenReturn(Paginator.validate(pageableRequestWithDates.page(), pageableRequestWithDates.size()));

        when(roomRepository.findAvailableRooms(
            pageableRequestWithDates.checkIn(),
            pageableRequestWithDates.checkOut(),
            hotelBookingMapper.toPageRequest(pageableRequestWithDates)))
            .thenReturn(roomPage);

        when(hotelBookingMapper.toRoomListResponse(roomPage))
            .thenReturn(roomListResponse);

        RoomListResponse<RoomDto> actualResponse = hotelBookingService.getAvailableRoomList(pageableRequestWithDates);

        assertThat(actualResponse).isEqualTo(roomListResponse);
    }

    @Test
    void bookRoomWithDates() {
    }

    @Test
    void getAvailableHotelList() {
    }
}