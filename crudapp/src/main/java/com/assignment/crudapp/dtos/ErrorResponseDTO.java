package com.assignment.crudapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private String errorMsg;
    @JsonFormat(pattern = "dd MMM yyyy HH:mm:ss z")
    private Date timestamp;

    public ErrorResponseDTO(String errorMsg) {
        this.errorMsg = errorMsg;
        this.timestamp = new Date();
    }
}
