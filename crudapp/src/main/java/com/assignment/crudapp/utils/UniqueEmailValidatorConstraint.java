package com.assignment.crudapp.utils;

import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.services.ApplicationUserService;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
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
