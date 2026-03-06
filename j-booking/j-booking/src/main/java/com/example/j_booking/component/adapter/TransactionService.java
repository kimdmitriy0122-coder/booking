package com.example.j_booking.component.adapter;

import com.example.j_booking.configuration.TransactionApiProperties;
import com.example.j_booking.dto.request.PaymentRequest;
import com.example.j_booking.dto.response.PaymentResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {
    RestClient restClient;

    public PaymentResponse sendPayment(PaymentRequest request, TransactionApiProperties transactionApiProperties){
        return restClient
            .post()
            .uri(transactionApiProperties.getUrl())
            .body(request)
            .retrieve()
            .body(PaymentResponse.class);

    }

//    public PaymentResponse getStatus(UUID referenceId, TransactionApiProperties props) {
//
//        return restClient
//            .get()
//            .uri(props.getStatusUrl() + "/" + referenceId)
//            .retrieve()
//            .body(PaymentResponse.class);
//    }
}
