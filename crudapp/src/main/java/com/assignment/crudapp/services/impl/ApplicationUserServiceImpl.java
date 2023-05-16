package com.assignment.crudapp.services.impl;

import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.repositories.ApplicationUserRepository;
import com.assignment.crudapp.services.ApplicationUserService;
import com.assignment.crudapp.utils.Constants;
import org.apache.catalina.User;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {
    @Autowired
    ApplicationUserRepository appUserRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Long createUser(UserDTO newUser) {
        ApplicationUser savedUser = appUserRepo.save(modelMapper.map(newUser, ApplicationUser.class));
        return savedUser.getId();
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<ApplicationUser> userById = appUserRepo.findById(id);


        if(userById.isEmpty()) {
            throw new RuntimeException(String.format(Constants.USER_NOT_FOUND_ERROR_MSG, id));
        }

        return modelMapper.map(userById.get(), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> response = new ArrayList<>();
        List<ApplicationUser> allUsers = appUserRepo.findAll();
        allUsers.forEach(appUser -> response.add(modelMapper.map(appUser, UserDTO.class)));
        return response;
    }

    @Override
    public void updateUser(Long id, UserDTO updatedUserInfo) {
        UserDTO existingUser = getUserById(id);
        updateUserInfo(existingUser, updatedUserInfo);

        appUserRepo.save(modelMapper.map(existingUser, ApplicationUser.class));
    }

    @Override
    public void deleteUser(Long id) {
        UserDTO existingUser = getUserById(id);
        appUserRepo.delete(modelMapper.map(existingUser, ApplicationUser.class));
    }

    @Override
    public boolean isUniqueEmail(String email) {
        Optional<ApplicationUser> userByEmail = appUserRepo.findByEmail(email);
        return userByEmail.isEmpty();
    }

    private void updateUserInfo(UserDTO existingUser, UserDTO updatedUserInfo) {
        existingUser.setName(updatedUserInfo.getName() != null ? updatedUserInfo.getName() : existingUser.getName());
        existingUser.setEmail(updatedUserInfo.getEmail() != null ? updatedUserInfo.getEmail() : existingUser.getEmail());
        existingUser.setBirthDate(updatedUserInfo.getBirthDate() != null ? updatedUserInfo.getBirthDate() : existingUser.getBirthDate());
    }
}
