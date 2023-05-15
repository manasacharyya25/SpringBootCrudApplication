package com.assignment.crudapp.dtos;

import com.assignment.crudapp.utils.EmailValidatorAnnotation;
import com.assignment.crudapp.utils.UniqueEmailValidatorAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    @NotNull(message = "Name cannot be Null")
    private String name;
    @NotNull(message = "Email cannot be Null")
    @EmailValidatorAnnotation
    @UniqueEmailValidatorAnnotation
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
