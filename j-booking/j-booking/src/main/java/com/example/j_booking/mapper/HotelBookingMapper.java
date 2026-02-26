package com.example.j_booking.mapper;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.dto.HotelBookingResponse;
import com.example.j_booking.dto.PageableRequest;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.utils.Paginator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.PageRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelBookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    BookingRecord toBookingRecord(HotelBookingRequest dto);

    @Mapping(target = "message", source = "message")
    HotelBookingResponse toHotelBookingResponse(BookingRecord dto, String message);

    @Mapping(target = "pageNumber", source = "page")
    @Mapping(target = "pageSize", expression = "java(Math.min(request.size(), Paginator.MAX_PAGE_SIZE))")
    // MapStruct не может создать PageRequest через сеттеры, поэтому используем фабричный метод
    default PageRequest toPageRequest(PageableRequest request) {
        return Paginator.validate(request.page(), request.size());
    }
}
