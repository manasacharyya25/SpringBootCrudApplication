package com.assignment.crudapp.dtos;

import com.assignment.crudapp.validations.EmailValidatorAnnotation;
import com.assignment.crudapp.validations.UniqueEmailValidatorAnnotation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String name;
    @EmailValidatorAnnotation
    @UniqueEmailValidatorAnnotation
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
