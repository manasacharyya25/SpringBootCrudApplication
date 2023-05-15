package com.assignment.crudapp.controllers;

import com.assignment.crudapp.models.ApplicationUser;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    ApplicationUserServiceImpl appUserService;

    @GetMapping("test")
    public ResponseEntity<?> testApplication() {
        ApplicationUser response = appUserService.getTestUser();
        return ResponseEntity.ok(response);
    }

}
