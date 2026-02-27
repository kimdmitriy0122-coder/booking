package com.example.j_booking.validation;

import com.example.j_booking.dto.request.HotelBookingRequest;
import com.example.j_booking.validation.annotations.HotelBookingRequestValidationAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HotelBookingRequestValidation implements ConstraintValidator<HotelBookingRequestValidationAnnotation, HotelBookingRequest> {

    @Override
    public boolean isValid(HotelBookingRequest request,
                           ConstraintValidatorContext context) {
        if (request == null || request.checkIn() == null || request.checkOut() == null) {
            return true;
            // сделано по рекомендации ГПТ:
            // по правилам Bean Validation должно быть:
            // null → validator не проверяет
        }

        return request.checkOut().isAfter(request.checkIn());
    }
}
