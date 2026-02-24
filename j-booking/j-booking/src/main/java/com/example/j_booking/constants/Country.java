package com.example.j_booking.constants;

public enum Country {

    UZBEKISTAN("UZ"),
    ENGLAND("EN"),
    USA("US"),
    RUSSIA("RU"),
    GERMANY("DE"),
    ITALY("IT"),
    JAPAN("JP"),
    FRANCE("FR");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

