package com.assignment.crudapp.services;

import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.exceptions.RecordNotFoundException;
import com.assignment.crudapp.models.ApplicationUser;

import java.util.List;

public interface ApplicationUserService {
    Long createUser(UserDTO newUser);
    UserDTO getUserById(Long id) throws RecordNotFoundException;
    List<UserDTO> getAllUsers();
    void updateUser(Long id, UserDTO updatedUserInfo) throws RecordNotFoundException;
    void deleteUser(Long id) throws RecordNotFoundException;
    boolean isUniqueEmail(String email);
}
