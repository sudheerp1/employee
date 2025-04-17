package com.backend.employee.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class DOBRangeValidator implements ConstraintValidator<DOBRange, LocalDate> {

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) return true; 
        int age = Period.between(dob, LocalDate.now()).getYears();
        return age >= 18 && age <= 90;
    }
}

