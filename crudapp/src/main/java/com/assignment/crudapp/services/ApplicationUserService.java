package com.assignment.crudapp.services;

import com.assignment.crudapp.models.ApplicationUser;

import java.util.List;

public interface ApplicationUserService {

    Long createUser(ApplicationUser newUser);
    ApplicationUser getUserById();
    List<ApplicationUser> getAllUsers();
}
