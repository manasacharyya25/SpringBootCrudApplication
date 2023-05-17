package com.assignment.crudapp;

import com.assignment.crudapp.dtos.ErrorResponseDTO;
import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.repositories.ApplicationUserRepository;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
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
        assertFalse(userService.isUniqueEmail("manas.acharyya@gmail.com"));
    }

    @Test
    public void testGetAllUsersReturnsAllUsersFromDB() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse = restTemplate.postForEntity("/users", user, Long.class);

        // send a GET request to the /users endpoint
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );

        // verify that the response has an HTTP status code of 200 OK and contains the same user in the response body
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

        // send a GET request to the /users/{id} endpoint
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                newUserUri,
                HttpMethod.GET,
                null,
                UserDTO.class
        );

        // verify that the response has an HTTP status code of 200 OK and contains the same user in the response body
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

        // create a new UserDTO with updated details
        UserDTO updatedUser = new UserDTO();
        updatedUser.setName("John Doe");
        updatedUser.setName("jd@gmail.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(updatedUser, headers);

        // Make the PUT request
        ResponseEntity<String> updatedResponse = restTemplate.exchange(newUserUri, HttpMethod.PUT, requestEntity, String.class);


        // send a GET request to the /users/{id} endpoint
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                newUserUri,
                HttpMethod.GET,
                null,
                UserDTO.class
        );

        // verify that the response has an HTTP status code of 200 OK and contains updated user info in the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser.getName(), response.getBody().getName());
        assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void testDeleteUserDeletesSpecifiedUserFromDB() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse = restTemplate.postForEntity("/users", user, Long.class);
        String newUserUri = createResponse.getHeaders().get("Location").get(0);

        // send a GET request to the /users endpoint
        ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );

        // verify that the response has an HTTP status code of 200 OK and contains 1 user in DB
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());

        // send a GET request to the /users endpoint
        restTemplate.delete(newUserUri);

        // send a GET request to the /users endpoint
        response = restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );

        // verify that the response has an HTTP status code of 200 OK and contains the 0 Users in DB
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    public void testGetUserByIdThrowsExceptionForInvalidId() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse = restTemplate.postForEntity("/users", user, Long.class);
        String newUserUri = createResponse.getHeaders().get("Location").get(0);

        // send a GET request to the /users/{id} endpoint
        ResponseEntity<ErrorResponseDTO> response = restTemplate.getForEntity("/users/10", ErrorResponseDTO.class);


        // verify that the response has an HTTP status code of 404 NOT FOUND and contains error message in response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User Not Found with Id 10", response.getBody().getErrorMsg());

    }

    @Test
    public void testCreateUserWithDuplicateEmailThrowsException() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse = restTemplate.postForEntity("/users", user, Long.class);

        // send a POST request to the /users endpoint with the same user email
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<List<ErrorResponseDTO>> createResponse2 = restTemplate.exchange(
                "/users",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<ErrorResponseDTO>>() {}
        );

        // verify that the response has an HTTP status code of 400 BAD REQUEST and contains error message in response
        assertEquals(HttpStatus.BAD_REQUEST, createResponse2.getStatusCode());
        assertEquals("Email already in use.", createResponse2.getBody().get(0).getErrorMsg());

    }

    @Test
    public void testUpdateUserWithExistingEmailThrowsException() {
        //create record
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse = restTemplate.postForEntity("/users", user, Long.class);

        //create Another record
        UserDTO user2 = new UserDTO();
        user2.setName("John Doe");
        user2.setEmail("john.doe@gmail.com");

        // send a POST request to the /users endpoint with the user object as the request body
        ResponseEntity<Long> createResponse2 = restTemplate.postForEntity("/users", user2, Long.class);
        String newUserUri = createResponse2.getHeaders().get("Location").get(0);

        // send a PUT request to the newUserUri endpoint with the updated user using existing email
        UserDTO user3 = new UserDTO();
        user3.setEmail("manas.acharyya@gmail.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(user3, headers);
        ResponseEntity<List<ErrorResponseDTO>> updateUserResponse = restTemplate.exchange(
                newUserUri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<List<ErrorResponseDTO>>() {}
        );

        // verify that the response has an HTTP status code of 400 BAD REQUEST and contains error message in response
        assertEquals(HttpStatus.BAD_REQUEST, updateUserResponse.getStatusCode());
        assertEquals("Email already in use.", updateUserResponse.getBody().get(0).getErrorMsg());
    }
}
