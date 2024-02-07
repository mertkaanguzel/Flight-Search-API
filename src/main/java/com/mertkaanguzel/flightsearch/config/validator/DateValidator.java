package com.mertkaanguzel.flightsearch.config.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;
import java.time.LocalDate;

public class DateValidator implements
        ConstraintValidator<isValidDate, String> {


    public DateValidator() {
    }

    @Override
    public void initialize(isValidDate dateString) {
    }

    @Override
    public boolean isValid(String dateString,
                           ConstraintValidatorContext cxt) {
        if (dateString == null) return true;

        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
