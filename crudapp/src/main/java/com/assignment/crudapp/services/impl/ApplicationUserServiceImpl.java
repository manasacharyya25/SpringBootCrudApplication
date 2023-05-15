package com.assignment.crudapp.services.impl;

import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.repositories.ApplicationUserRepository;
import com.assignment.crudapp.services.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public ApplicationUser getUserById() {
        return null;
    }

    @Override
    public List<ApplicationUser> getAllUsers() {
        List<ApplicationUser> allUsers = appUserRepo.findAll();
        return allUsers;
    }
}
