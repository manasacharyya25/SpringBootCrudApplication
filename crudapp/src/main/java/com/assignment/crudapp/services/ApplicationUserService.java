package com.assignment.crudapp.services;

import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.models.ApplicationUser;

import java.util.List;

public interface ApplicationUserService {
    Long createUser(UserDTO newUser);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    void updateUser(Long id, UserDTO updatedUserInfo);
    void deleteUser(Long id);
    boolean isUniqueEmail(String email);
}
