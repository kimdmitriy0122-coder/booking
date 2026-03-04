package com.example.j_booking.component.adapter;

import com.example.j_booking.dto.request.PaymentRequest;
import com.example.j_booking.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
public class TransactionService {
    RestClient restClient;

    public PaymentResponse sendPayment(PaymentRequest request){
        return restClient
            .post()
            .body(request)
            .retrieve()
            .body(PaymentResponse.class);

    }
}
