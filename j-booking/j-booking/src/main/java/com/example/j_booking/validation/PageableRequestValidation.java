package com.example.j_booking.validation;

import com.example.j_booking.dto.request.PageableRequestWithDates;
import com.example.j_booking.validation.annotations.PageableRequestValidationAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageableRequestValidation implements ConstraintValidator<PageableRequestValidationAnnotation, PageableRequestWithDates> {

    @Override
    public boolean isValid(PageableRequestWithDates request, ConstraintValidatorContext constraintValidatorContext) {
        if (request == null || request.checkIn() == null || request.checkOut() == null) {
            return true;
            // сделано по рекомендации ГПТ:
            // по правилам Bean Validation должно быть:
            // null → validator не проверяет
        }

        return request.checkOut().isAfter(request.checkIn());
    }
}
