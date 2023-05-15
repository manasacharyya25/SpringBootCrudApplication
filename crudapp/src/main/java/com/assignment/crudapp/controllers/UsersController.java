package com.assignment.crudapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersController {

    @GetMapping("test")
    public ResponseEntity<?> testApplication() {
        return ResponseEntity.ok("CRUD Application Working !!!");
    }

}
