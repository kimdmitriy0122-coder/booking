package com.example.j_booking.mapper;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.entity.BookingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface HotelBookingMapper {

    @Mapping(target = "id", ignore = true)
    BookingRecord toEntity(HotelBookingRequest dto);

//    @Mapping(target = "")
//    HotelBookingResponse toResponse(BookingRecord entity);
}
