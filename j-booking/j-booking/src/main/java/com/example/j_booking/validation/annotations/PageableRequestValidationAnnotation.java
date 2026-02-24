package com.example.j_booking.validation.annotations;

import com.example.j_booking.validation.HotelBookingRequestValidation;
import com.example.j_booking.validation.PageableRequestValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PageableRequestValidation.class)
public @interface PageableRequestValidationAnnotation {
    String message() default "check out date must be after check in date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
