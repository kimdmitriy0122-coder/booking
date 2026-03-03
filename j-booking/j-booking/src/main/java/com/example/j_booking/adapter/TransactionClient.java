package com.example.j_booking.adapter;

import com.example.j_booking.dto.request.PaymentRequest;
import com.example.j_booking.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
public class TransactionClient {
    RestClient restClient;

    @PostMapping
    public PaymentResponse sendPayment(PaymentRequest request){
        return restClient
                .get()
                .retrieve()
                .body(PaymentResponse.class);

    }
}
