package com.example.j_booking.utils;


import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class Paginator {

    public static final int MAX_PAGE_SIZE = 100;

    public static PageRequest validate(int page, int size) {
        return PageRequest.of(page, Math.min(size, MAX_PAGE_SIZE));
    }
}
