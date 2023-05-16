package com.assignment.crudapp.validations;

import com.assignment.crudapp.services.ApplicationUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UniqueEmailValidatorConstraint implements ConstraintValidator<UniqueEmailValidatorAnnotation, String> {
    @Autowired
    ApplicationUserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.isUniqueEmail(email);
    }

    @Override
    public void initialize(UniqueEmailValidatorAnnotation uniqueEmailValidatorAnnotation) {
        ConstraintValidator.super.initialize(uniqueEmailValidatorAnnotation);
    }

}
