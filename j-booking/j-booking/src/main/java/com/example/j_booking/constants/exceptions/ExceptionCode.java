package com.example.j_booking.constants.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_VALID_CHECKOUT_DATE(10000, "checkout date must be after checkin date");

    int code;
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
