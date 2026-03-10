package com.example.j_booking.controller;

import com.example.j_booking.constants.*;
import com.example.j_booking.dto.HotelDto;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.request.PageableRequestWithDates;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.response.HotelListResponse;
import com.example.j_booking.dto.response.RoomListResponse;
import com.example.j_booking.entity.Address;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import com.example.j_booking.mapper.HotelBookingMapper;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.repository.HotelRepository;
import com.example.j_booking.repository.RoomRepository;
import com.example.j_booking.service.RoomService;
import com.example.j_booking.service.SaverBookingService;
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
    HotelRepository hotelRepository;
    @Mock
    HotelBookingMapper hotelBookingMapper;
    @Mock
    RoomService roomService;
    @Mock
    SaverBookingService saverBookingService;

    @InjectMocks
    HotelBookingServiceImpl hotelBookingService;

    Room room1, room2, room3, room4;
    RoomDto roomDto1, roomDto2, roomDto3, roomDto4;
    Hotel hotel1, hotel2, hotel3, hotel4;
    HotelDto hotelDto1, hotelDto2, hotelDto3, hotelDto4;
    BookingRecord recordBooked, recordFree, recordFreeAfterProcessing;
    HotelBookingRequest request, request4;
    HotelBookingResponse requiredResponseFailed;
    HotelBookingResponse requiredResponseSuccess;
    PageableRequest pageableRequest;
    PageableRequestWithDates pageableRequestWithDates;
    Page<RoomDto> roomDtoPage;
    Page<Room> roomPage;
    Page<Hotel> hotelPage;
    Page<HotelDto> hotelDtoPage;
    RoomListResponse roomListResponse;
    HotelListResponse hotelListResponse;
    Address address;

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
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.STANDARD)
            .build();

        roomDto2 = RoomDto
            .builder()
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.LUX)
            .build();

        roomDto3 = RoomDto
            .builder()
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.PRESIDENT_LUX)
            .build();

        roomDto4 = RoomDto
            .builder()
            .hotelId(1L)
            .roomCapacity(RoomCapacity.DOUBLE)
            .roomClass(RoomClass.SUPERIOR)
            .build();

        recordBooked = BookingRecord
                .builder()
                .room(room1)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.BOOKED)
                .created(LocalDateTime.now())
                .build();

        recordFree = BookingRecord
                .builder()
                .room(room4)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.FREE)
                .created(LocalDateTime.now())
                .build();

        recordFreeAfterProcessing = BookingRecord
                .builder()
                .room(room4)
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

        request4 = HotelBookingRequest
                .builder()
                .roomId(4L)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .build();

        requiredResponseFailed = HotelBookingResponse
                .builder()
                .room(room1)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.BOOKED)
                .message("can't book room because it's already booked")
                .build();

        requiredResponseSuccess = HotelBookingResponse
                .builder()
                .room(room1)
                .checkIn(LocalDate.of(2026, 1, 10))
                .checkOut(LocalDate.of(2026, 1, 11))
                .status(BookingStatus.FREE)
                .message("room booked")
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

        address = Address
                .builder()
                .city(City.BERLIN)
                .buildingNumber("1E")
                .street("Strasse 21")
                .country(Country.GERMANY)
                .build();

        hotel1 = Hotel
                .builder()
                .rooms(List.of(room1, room2, room3, room4))
                .name("Astoris")
                .address(address)
                .buildingType(BuildingType.APARTMENT)
                .rating((short) 3)
                .build();

        hotelDto1 = HotelDto
                .builder()
                .name("Astoris")
                .address(address)
                .buildingType(BuildingType.APARTMENT)
                .rating((short) 3)
                .build();


        hotelPage = new PageImpl<>(List.of(hotel1));

        hotelDtoPage = new PageImpl<>(List.of(hotelDto1));

        hotelListResponse = HotelListResponse
                .<HotelDto>builder()
                .content(hotelDtoPage.toList())
                .page(0)
                .size(5)
                .build();

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

        when(hotelBookingMapper.toBookingRecord(request)).thenReturn(recordBooked);
//        when(roomRepository.findById(room1.getId())).thenReturn(Optional.of(room1));
        when(hotelBookingService.checkRoomAvailabilityWithDates(request)).thenReturn(requiredResponseFailed);

        HotelBookingResponse actualResponse = hotelBookingService.checkRoomAvailabilityWithDates(request);

        assertThat(actualResponse).isEqualTo(requiredResponseFailed);
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
    void bookRoomWithDatesFailed() {
        when(hotelBookingMapper.toBookingRecord(request))
                .thenReturn(recordBooked);
        when(roomService.getRoomById(1L))
                .thenReturn(room1);
        when(bookingRecordRepository.isRoomFreeByDates(recordBooked))
                .thenReturn(true);
        when(hotelBookingMapper.toHotelBookingResponse(recordBooked,"can't book room because it's already booked")).thenReturn(requiredResponseFailed);

        HotelBookingResponse actualResponseFailed = hotelBookingService.bookHotelRoomByRequest(request);

        assertThat(actualResponseFailed).isEqualTo(requiredResponseFailed);
    }

    @Test
    void bookRoomWithDatesSuccess() {
        when(hotelBookingMapper.toBookingRecord(request4))
                .thenReturn(recordFree);
        when(roomService.getRoomById(4L))
                .thenReturn(room4);
        when(bookingRecordRepository.isRoomFreeByDates(recordFree))
                .thenReturn(false);
        when(saverBookingService.saveBookingRecord(recordFree, BookingStatus.BOOKED))
                .thenAnswer(invocation -> {
                    BookingRecord record = invocation.getArgument(0);
                    BookingStatus status = invocation.getArgument(1);

                    record.setStatus(status);
                    return record;
                });
        when(hotelBookingMapper.toHotelBookingResponse(recordFree,"room booked"))
                .thenReturn(requiredResponseSuccess);

        HotelBookingResponse actualResponseSuccess = hotelBookingService.bookHotelRoomByRequest(request4);

        assertThat(actualResponseSuccess).isEqualTo(requiredResponseSuccess);
    }

    @Test
    void getAvailableHotelList() {
        when(hotelRepository.findAvailableHotels(
                pageableRequestWithDates.checkIn(),
                pageableRequestWithDates.checkOut(),
                hotelBookingMapper.toPageRequest(pageableRequestWithDates)))
                .thenReturn(hotelPage);

        when(hotelBookingMapper.toHotelListResponse(hotelPage))
                .thenReturn(hotelListResponse);

        HotelListResponse<HotelDto> actualResponse = hotelBookingService.getAvailableHotelList(pageableRequestWithDates);

        assertThat(actualResponse).isEqualTo(hotelListResponse);
    }
}