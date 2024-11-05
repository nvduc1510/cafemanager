package com.example.BackEnd.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApiResponse {
    private Integer status;
    private String message;
    private long total;
    private Object params;
    public ApiResponse (Integer status, String message, Object params) {
        this.status = status;
        this.message = message;
        this.params = params;
    }
    public ApiResponse (Integer status, String message) {
        this.status = status;
        this.message = message;
    }
    public ApiResponse (String message) {
        this.message = message;
    }

}
