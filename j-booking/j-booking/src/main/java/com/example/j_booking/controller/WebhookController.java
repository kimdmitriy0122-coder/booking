package com.example.j_booking.controller;

import com.example.j_booking.constants.BookingStatus;
import com.example.j_booking.constants.TransactionStatus;
import com.example.j_booking.dto.response.PaymentResponse;
import com.example.j_booking.entity.BookingRecord;
import com.example.j_booking.repository.BookingRecordRepository;
import com.example.j_booking.service.SaverBookingService;
import com.example.j_booking.service.impl.WebhookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final BookingRecordRepository bookingRecordRepository;
    private final SaverBookingService saverBookingService;

    @PostMapping("/transaction")
    public ResponseEntity<Void> transactionWebhook(@RequestBody PaymentResponse response){

        BookingRecord record =
            bookingRecordRepository.getBookingRecordByPaymentId(response.referenceId());

        if(response.status() == TransactionStatus.COMPLETED){

            record.setStatus(BookingStatus.PAYED);

            saverBookingService.updateStatus(record);
        }

        return ResponseEntity.ok().build();
    }
}