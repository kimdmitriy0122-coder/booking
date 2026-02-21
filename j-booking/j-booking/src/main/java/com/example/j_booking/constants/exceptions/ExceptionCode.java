package com.example.j_booking.constants.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_VALID_REQUEST(10000, "check your request"),
    UNEXPECTED_TYPE(10001, "unexpected type"),
    NO_SUCH_ROOM(10002, "no such room"),;

    int code;
    String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
