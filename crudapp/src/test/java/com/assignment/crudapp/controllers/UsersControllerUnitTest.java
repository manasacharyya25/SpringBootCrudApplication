package com.assignment.crudapp.controllers;

import com.assignment.crudapp.CrudApplication;
import com.assignment.crudapp.dtos.UserDTO;
import com.assignment.crudapp.services.impl.ApplicationUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CrudApplication.class)
@AutoConfigureMockMvc
public class UsersControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationUserServiceImpl userService;

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("[]"));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        when(userService.isUniqueEmail(anyString())).thenReturn(true);
        when(userService.createUser(any(UserDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Manas Acharyya\",\"email\": \"manas.acharyya@gmail.com\"}"))
                .andExpect(status().isCreated());

        verify(userService, times(1)).isUniqueEmail("manas.acharyya@gmail.com");
        verify(userService, times(1)).createUser(eq(user));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setName("Manas Acharyya");
        user.setEmail("manas.acharyya@gmail.com");

        when(userService.isUniqueEmail(anyString())).thenReturn(true);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Manas Acharyya\",\"email\": \"manas.acharyya@gmail.com\"}"))
                .andExpect(status().isOk());
        verify(userService, times(1)).isUniqueEmail("manas.acharyya@gmail.com");
        verify(userService, times(1)).updateUser(1L, user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(1L);
    }
}
