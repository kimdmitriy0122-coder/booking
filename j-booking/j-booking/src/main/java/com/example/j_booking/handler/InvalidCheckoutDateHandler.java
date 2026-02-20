package com.example.j_booking.handler;

import com.example.j_booking.constants.exceptions.ExceptionCode;
import com.example.j_booking.dto.error.CommonErrorResponse;
import com.example.j_booking.exceptions.InvalidCheckoutDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidCheckoutDateHandler {
    @ExceptionHandler(InvalidCheckoutDate.class)
    public ResponseEntity<CommonErrorResponse> invalidCheckoutDate() {
        return ResponseEntity
            .badRequest()
            .body(new CommonErrorResponse(
                ExceptionCode.NOT_VALID_CHECKOUT_DATE.getCode(),
                ExceptionCode.NOT_VALID_CHECKOUT_DATE.getMessage())
            );
    }
}
