package com.assignment.crudapp.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidatorConstraint.class)
public @interface UniqueEmailValidatorAnnotation {
    String message() default "Email already in use.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
