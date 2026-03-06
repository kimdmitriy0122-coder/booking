package com.example.j_booking.exceptions;

public class BookingRecordWithSuchPaymentIdNotExist extends RuntimeException {
    public BookingRecordWithSuchPaymentIdNotExist(String message) {
        super(message);
    }
}
