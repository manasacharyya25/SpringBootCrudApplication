package com.assignment.crudapp.utils;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidatorConstraint implements ConstraintValidator<EmailValidatorAnnotation, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }

        return Pattern.matches("^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email);
    }

    @Override
    public void initialize(EmailValidatorAnnotation emailValidatorAnnotation) {
    }

}
