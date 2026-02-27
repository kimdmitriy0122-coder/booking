package com.example.j_booking.validation;

import com.example.j_booking.dto.request.PageableRequest;
import com.example.j_booking.validation.annotations.PageableRequestValidationAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageableRequestValidation implements ConstraintValidator<PageableRequestValidationAnnotation, PageableRequest> {

    @Override
    public boolean isValid(PageableRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if (request == null || request.checkIn() == null || request.checkOut() == null) {
            return true;
            // сделано по рекомендации ГПТ:
            // по правилам Bean Validation должно быть:
            // null → validator не проверяет
        }

        return request.checkOut().isAfter(request.checkIn());
    }
}
