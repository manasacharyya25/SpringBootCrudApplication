package com.assignment.crudapp.services.impl;

import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.services.ApplicationUserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    @Override
    public ApplicationUser getTestUser() {
        ApplicationUser response = new ApplicationUser(
                                        "John Doe",
                                        "john.doe@gmail.com",
                                        new Date());
        return response;
    }
}
