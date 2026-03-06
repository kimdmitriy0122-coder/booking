package com.example.j_booking.service.impl;

import com.example.j_booking.constants.TransactionStatus;
import com.example.j_booking.dto.request.PaymentRequest;
import com.example.j_booking.dto.response.PaymentResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookService {
    RestClient restClient;

    @Async
    public void simulateWebhook(PaymentRequest request){

        try {
            Thread.sleep(5000); // имитация обработки платежа
        } catch (InterruptedException ignored) {}

        PaymentResponse response = new PaymentResponse(
            UUID.randomUUID(),
            request.referenceId(),
            TransactionStatus.COMPLETED,
            request.amount(),
            request.currency(),
            LocalDateTime.now()
        );

        restClient.post()
            .uri("http://localhost:8080/webhook/transaction")
            .body(response)
            .retrieve()
            .toBodilessEntity();
    }
}
