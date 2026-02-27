package com.example.j_booking.mapper;

import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.dto.response.HotelBookingResponse;
import com.example.j_booking.dto.HotelDto;
import com.example.j_booking.dto.response.HotelListResponse;
import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.dto.RoomDto;
import com.example.j_booking.dto.response.RoomListResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.entity.Hotel;
import com.example.j_booking.entity.Room;
import com.example.j_booking.utils.Paginator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelBookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "paymentId", ignore = true)
    BookingRecord toBookingRecord(HotelBookingRequest dto);

    @Mapping(target = "message", source = "message")
    HotelBookingResponse toHotelBookingResponse(BookingRecord dto, String message);

    @Mapping(target = "pageNumber", source = "page")
    @Mapping(target = "pageSize", expression = "java(Math.min(request.size(), Paginator.MAX_PAGE_SIZE))")
    // MapStruct не может создать PageRequest через сеттеры, поэтому используем фабричный метод
    default PageRequest toPageRequest(PageableRequest request) {
        return Paginator.validate(request.page(), request.size());
    }

    List<RoomDto> toRoomDtoList(List<Room> rooms);
    RoomDto toDto(Room room);

    List<HotelDto> toHotelDtoList(List<Hotel> hotels);
    HotelDto toDto(Hotel hotel);

    default RoomListResponse<RoomDto> toRoomListResponse(Page<Room> page) {
        return new RoomListResponse<>(
            toRoomDtoList(page.getContent()),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }

    default HotelListResponse<HotelDto> toHotelListResponse(Page<Hotel> page) {
        return new HotelListResponse<>(
            toHotelDtoList(page.getContent()),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
}
