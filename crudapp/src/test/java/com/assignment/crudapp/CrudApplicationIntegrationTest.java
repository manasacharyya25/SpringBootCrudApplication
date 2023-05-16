package com.assignment.crudapp;

import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.repositories.ApplicationUserRepository;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudApplicationIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationUserServiceImpl userService;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Before
    public void setUp() {
        // clear the user repository before each test
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUserCreatesARecordInDB() {
        // create a user object with a unique email address
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> response = restTemplate.postForEntity("/users", user, Long.class);

        // verify that the response has an HTTP status code of 200 OK and contains the same email address in the response body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetAllUsersReturnsAllUsersFromDB() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse = restTemplate.postForEntity("/users", user, Long.class);

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );

        // verify that the response has an HTTP status code of 200 OK and contains the same email address in the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(user.getName(), response.getBody().get(0).getName());
        assertEquals(user.getEmail(), response.getBody().get(0).getEmail());
    }

    @Test
    public void testGetUserByIdReturnsSpecificUserFromDB() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/users", user, String.class);
        String newUserUri = createResponse.getHeaders().get("Location").get(0);

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                newUserUri,
                HttpMethod.GET,
                null,
                UserDTO.class
        );

        // verify that the response has an HTTP status code of 200 OK and contains the same email address in the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getName(), response.getBody().getName());
        assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void testPutUserUpdatesSpecifiedUserInDB() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/users", user, String.class);
        String newUserUri = createResponse.getHeaders().get("Location").get(0);
        String newUserId = newUserUri.split("/")[1];

        UserDTO updatedUser = new UserDTO();
        updatedUser.setName("John Doe");
        updatedUser.setName("jd@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the request body and headers
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(updatedUser, headers);

        // Make the PUT request
        ResponseEntity<String> updatedResponse = restTemplate.exchange(newUserUri, HttpMethod.PUT, requestEntity, String.class);



        ResponseEntity<UserDTO> response = restTemplate.exchange(
                newUserUri,
                HttpMethod.GET,
                null,
                UserDTO.class
        );

        // verify that the response has an HTTP status code of 200 OK and contains the same email address in the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser.getName(), response.getBody().getName());
        assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void testDeleteUserDeletesSpecifiedUserFromDB() {

    }

    @Test
    public void testGetUserByIdThrowsExceptionForInvalidId() {

    }

    @Test
    public void testCreateUserWithDuplicateEmailThrowsException() {

    }

    @Test
    public void testUpdateUserWithExistingEmailThrowsException() {

    }
}
