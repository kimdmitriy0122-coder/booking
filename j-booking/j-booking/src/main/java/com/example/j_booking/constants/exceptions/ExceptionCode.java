package com.example.j_booking.constants.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_VALID_REQUEST(10000, "check your request"),
    UNEXPECTED_TYPE(10001, "unexpected type"),
    NO_SUCH_ROOM(10002, "no such room"),
    INVALID_CHECKOUT_DATE(10003, "checkout date invalid. must be after checkin date"),
    NOT_READABLE_REQUEST(10004, "not readable request"),
    RESOURCE_ACCESS(10005, "resource access denied"),
    PAYMENT_DOESNT_EXISTS(10006, "such payment does not exist"),
    BOOKING_ALREADY_PAYED(10006, "this booking already payed"),
    ;

    int code;
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
