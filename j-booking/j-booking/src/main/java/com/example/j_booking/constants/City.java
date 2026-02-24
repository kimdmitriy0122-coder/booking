package com.example.j_booking.constants;

public enum City {

    // Uzbekistan
    TASHKENT(Country.UZBEKISTAN),
    SAMARKAND(Country.UZBEKISTAN),
    BUKHARA(Country.UZBEKISTAN),

    // England
    LONDON(Country.ENGLAND),
    MANCHESTER(Country.ENGLAND),
    LIVERPOOL(Country.ENGLAND),

    // USA
    NEW_YORK(Country.USA),
    LOS_ANGELES(Country.USA),
    CHICAGO(Country.USA),

    // Russia
    MOSCOW(Country.RUSSIA),
    SAINT_PETERSBURG(Country.RUSSIA),
    KAZAN(Country.RUSSIA),

    // Germany
    BERLIN(Country.GERMANY),
    MUNICH(Country.GERMANY),
    HAMBURG(Country.GERMANY),

    PARIS(Country.FRANCE),
    MARSEILLE(Country.FRANCE),
    LYON(Country.FRANCE),

    ROME(Country.ITALY),
    MILAN(Country.ITALY),
    VENICE(Country.ITALY),

    TOKYO(Country.JAPAN),
    OSAKA(Country.JAPAN),
    KYOTO(Country.JAPAN);

    private final Country country;

    City(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}

