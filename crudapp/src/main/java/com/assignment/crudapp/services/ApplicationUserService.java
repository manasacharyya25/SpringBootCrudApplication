package com.assignment.crudapp.services;

import com.assignment.crudapp.models.ApplicationUser;

import java.util.List;

public interface ApplicationUserService {
    Long createUser(ApplicationUser newUser);
    ApplicationUser getUserById(Long id);
    List<ApplicationUser> getAllUsers();
    void updateUser(Long id, ApplicationUser updatedUserInfo);
    void deleteUser(Long id);
}
