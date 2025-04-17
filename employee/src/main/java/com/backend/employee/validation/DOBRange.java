package com.backend.employee.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DOBRangeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DOBRange {
    String message() default "Age must be between 18 and 90 years";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
