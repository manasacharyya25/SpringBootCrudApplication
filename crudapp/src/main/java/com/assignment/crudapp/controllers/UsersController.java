package com.assignment.crudapp.controllers;

import com.assignment.crudapp.models.ApplicationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("users")
public class UsersController {

    @GetMapping("test")
    public ResponseEntity<?> testApplication() {
        ApplicationUser response = new ApplicationUser("John Doe", "john.doe@gmail.com", new Date());
        return ResponseEntity.ok(response);
    }

}
