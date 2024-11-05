package com.example.BackEnd.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerExceptionResponse {
    private Integer status;
    private String message;
    private String timestamp;
}
