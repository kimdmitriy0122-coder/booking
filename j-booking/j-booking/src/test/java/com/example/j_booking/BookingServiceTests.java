package com.example.j_booking;

import com.example.j_booking.entity.Room;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.service.HotelBookingService;
import com.example.j_booking.service.impl.HotelBookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookingServiceTests {
    @Mock
    BookingRecordRepository bookingRecordRepository;
    @InjectMocks
    HotelBookingServiceImpl hotelService;
    @MockitoBean
    HotelBookingService hotelBookingService;

//    @Test
//    public Room getRoomById(UUID roomId) {
//        hotelService.getRoomById()
//    }


}
