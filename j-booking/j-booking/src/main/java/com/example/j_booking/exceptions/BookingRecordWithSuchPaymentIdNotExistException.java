package com.example.j_booking.exceptions;

public class BookingRecordWithSuchPaymentIdNotExistException extends RuntimeException {
    public BookingRecordWithSuchPaymentIdNotExistException(String message) {
        super(message);
    }
}
