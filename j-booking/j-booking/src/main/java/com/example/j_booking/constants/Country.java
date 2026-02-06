package com.example.j_booking.constants;

public enum Country {

    UZBEKISTAN("UZ"),
    ENGLAND("EN"),
    USA("US"),
    RUSSIA("RU"),
    GERMANY("DE");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

