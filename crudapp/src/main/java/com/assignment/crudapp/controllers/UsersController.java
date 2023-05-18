package com.assignment.crudapp.controllers;

import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.exceptions.RecordNotFoundException;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import com.assignment.crudapp.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    ApplicationUserServiceImpl appUserService;

    @GetMapping()
    @Operation(summary = "Get All Users", description = "This endpoint retrieves all users.")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = appUserService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping(Constants.USER_ID_PATH_VARIABLE)
    @Operation(summary = "Get User By Id", description = "This endpoint retrieves User with ID provided as Input. Responds 404 in case user with input ID doesn't exist.")
    public ResponseEntity<UserDTO> getUserById(@PathVariable  Long id) throws RecordNotFoundException {
        UserDTO user = appUserService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create User with Given Info", description = "This endpoint creates a new User with provided Info. Responds 400 in case input email already exist.")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO newUser) {
        Long newUserId = appUserService.createUser(newUser);
        return ResponseEntity.created(URI.create("/users/" + newUserId)).build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Update User with Given Info", description = "This endpoint updates User with input ID and provided Info. Responds 400 in case input email already exist.")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO updatedUserInfo) throws RecordNotFoundException {
        appUserService.updateUser(id, updatedUserInfo);
        return ResponseEntity.ok("User Updated.");
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletes User By Id", description = "This endpoint deletes User with ID provided as Input. Responds 404 in case user with input ID doesn't exist.")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws RecordNotFoundException {
        appUserService.deleteUser(id);
        return ResponseEntity.ok("User Deleted.");
    }
}
