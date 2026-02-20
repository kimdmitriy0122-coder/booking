package com.example.j_booking.validation.annotations;

import com.example.j_booking.validation.CheckoutDateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckoutDateValidation.class)
public @interface CheckoutDateValidationAnnotation {
    String message() default "invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
