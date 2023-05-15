package com.assignment.crudapp.controllers;

import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    ApplicationUserServiceImpl appUserService;

    @GetMapping()
    public ResponseEntity<List<ApplicationUser>> getAllUsers() {
        List<ApplicationUser> allUsers = appUserService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody ApplicationUser newUser) {
        Long newUserId = appUserService.createUser(newUser);
        return ResponseEntity.ok(newUserId);
    }
}
