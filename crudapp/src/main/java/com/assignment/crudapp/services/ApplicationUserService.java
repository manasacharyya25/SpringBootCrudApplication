package com.assignment.crudapp.services;

import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.models.ApplicationUser;

import java.util.List;

public interface ApplicationUserService {
    Long createUser(UserDTO newUser);
    ApplicationUser getUserById(Long id);
    List<ApplicationUser> getAllUsers();
    void updateUser(Long id, ApplicationUser updatedUserInfo);
    void deleteUser(Long id);
    boolean isUniqueEmail(String email);
}
