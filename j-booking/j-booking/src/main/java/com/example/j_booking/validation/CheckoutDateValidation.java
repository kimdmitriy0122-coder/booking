package com.example.j_booking.validation;

import com.example.j_booking.dto.HotelBookingRequest;
import com.example.j_booking.exceptions.InvalidCheckoutDate;
import com.example.j_booking.validation.annotations.CheckoutDateValidationAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckoutDateValidation implements ConstraintValidator<CheckoutDateValidationAnnotation, HotelBookingRequest> {

    @Override
    public boolean isValid(HotelBookingRequest request, ConstraintValidatorContext constraintValidatorContext) {
        if(request.checkOut().isBefore(request.checkIn()))
            throw new InvalidCheckoutDate();
        return false;
    }
}
