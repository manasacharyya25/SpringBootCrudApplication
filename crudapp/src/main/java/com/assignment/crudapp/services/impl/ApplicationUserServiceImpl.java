package com.assignment.crudapp.services.impl;

import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.repositories.ApplicationUserRepository;
import com.assignment.crudapp.services.ApplicationUserService;
import com.assignment.crudapp.utils.Constants;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    @Autowired
    ApplicationUserRepository appUserRepo;

    @Override
    public Long createUser(ApplicationUser newUser) {
        ApplicationUser savedUser = appUserRepo.save(newUser);
        return savedUser.getId();
    }

    @Override
    public ApplicationUser getUserById(Long id) {
        Optional<ApplicationUser> userById = appUserRepo.findById(id);

        if(userById.isEmpty()) {
            throw new RuntimeException(String.format(Constants.USER_NOT_FOUND_ERROR_MSG, id));
        }

        return userById.get();
    }

    @Override
    public List<ApplicationUser> getAllUsers() {
        List<ApplicationUser> allUsers = appUserRepo.findAll();
        return allUsers;
    }

    @Override
    public void updateUser(Long id, ApplicationUser updatedUserInfo) {
        ApplicationUser existingUser = getUserById(id);
        updateUserInfo(existingUser, updatedUserInfo);
        appUserRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        ApplicationUser existingUser = getUserById(id);
        appUserRepo.delete(existingUser);
    }

    private void updateUserInfo(ApplicationUser existingUser, ApplicationUser updatedUserInfo) {
        existingUser.setName(updatedUserInfo.getName() != null ? updatedUserInfo.getName() : existingUser.getName());
        existingUser.setEmail(updatedUserInfo.getEmail() != null ? updatedUserInfo.getEmail() : existingUser.getEmail());
        existingUser.setBirthDate(updatedUserInfo.getBirthDate() != null ? updatedUserInfo.getBirthDate() : existingUser.getBirthDate());
    }
}
