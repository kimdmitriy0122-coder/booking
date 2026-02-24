package com.example.j_booking.exceptions;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class InvalidCheckoutDate extends RuntimeException {
    public InvalidCheckoutDate() {
        super();
    }
}
