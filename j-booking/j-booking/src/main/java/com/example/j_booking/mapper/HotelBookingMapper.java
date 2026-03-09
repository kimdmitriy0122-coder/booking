package com.example.j_booking.mapper;

import com.example.j_booking.configuration.TransactionDataProperties;
import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.Currency;
import com.example.j_booking.constants.TransactionStatus;
import com.example.j_booking.constants.TransactionType;
import com.example.j_booking.dto.BookingRecordDto;
import com.example.j_booking.dto.PaymentDto;
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
    @Mapping(target = "paymentId", expression = "java(UUID.randomUUID())")
    BookingRecord toBookingRecord(HotelBookingRequest dto);

    @Mapping(target = "message", source = "message")
    HotelBookingResponse toHotelBookingResponse(BookingRecord dto, String message);

    List<RoomDto> toRoomDtoList(List<Room> rooms);
    RoomDto toDto(Room room);

    List<HotelDto> toHotelDtoList(List<Hotel> hotels);
    HotelDto toDto(Hotel hotel);

    List<BookingRecordDto> toBookingRecordDtoList(List<BookingRecord> hotels);
    BookingRecordDto toDto(BookingRecord record);

    @Mapping(target = "referenceId", source = "paymentDto.paymentId")
    @Mapping(target = "amount", expression = "java(transactionDataProperties.getAmount())")
    @Mapping(target = "type", expression = "java(transactionDataProperties.getType())")
    @Mapping(target = "currency", expression = "java(transactionDataProperties.getCurrency())")
    @Mapping(target = "merchantId", expression = "java(transactionDataProperties.getMerchantId())")
    @Mapping(target = "senderName", expression = "java(transactionDataProperties.getSenderName())")
    @Mapping(target = "senderToken", expression = "java(transactionDataProperties.getSenderToken())")
    @Mapping(target = "receiverName", expression = "java(transactionDataProperties.getReceiverName())")
    @Mapping(target = "receiverToken", expression = "java(transactionDataProperties.getReceiverToken())")
    PaymentRequest toPaymentRequest(PaymentDto paymentDto, TransactionDataProperties transactionDataProperties);


    default BookingRecord toBookingRecord(PaymentResponse paymentResponse, BookingRecord record){
        record.setStatus(
            switch (paymentResponse.status()){
                case CREATED,
                     SENDER_INFO_VALIDATED,
                     RECEIVER_INFO_VALIDATED,
                     AMOUNT_VALIDATED,
                     SENT_TO_CORE_LEDGER -> BookingStatus.PROCESSING;

                case COMPLETED -> BookingStatus.PAYED;

                case CALCULATE_FAILED,
                     RECEIVER_INFO_VALIDATION_FAILED,
                     AMOUNT_VALIDATION_FAILED,
                     SENDER_INFO_VALIDATION_FAILED,
                     FAILED -> BookingStatus.PROCESSING_FAILED;
            });
        return record;
    }

    @Mapping(target = "pageNumber", source = "page")
    @Mapping(target = "pageSize", expression = "java(Math.min(request.size(), Paginator.MAX_PAGE_SIZE))")
    // MapStruct не может создать PageRequest через сеттеры, поэтому используем фабричный метод
    default PageRequest toPageRequest(PageableRequestWithDates request) {
        return Paginator.validate(request.page(), request.size());
    }

    @Mapping(target = "pageNumber", source = "page")
    @Mapping(target = "pageSize", expression = "java(Math.min(request.size(), Paginator.MAX_PAGE_SIZE))")
    // MapStruct не может создать PageRequest через сеттеры, поэтому используем фабричный метод
    default PageRequest toPageRequest(PageableRequest request) {
        return Paginator.validate(request.page(), request.size());
    }


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

    default BookingRecordListResponse<BookingRecordDto> toBookingRecordListResponse(Page<BookingRecord> page) {
        return new BookingRecordListResponse<>(
            toBookingRecordDtoList(page.getContent())
        );
    }
}
