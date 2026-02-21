package com.example.j_booking.exceptions;

import java.util.NoSuchElementException;

public class NoSuchRoomException extends NoSuchElementException {
    public NoSuchRoomException(String message) {
        super(message);
    }
}
