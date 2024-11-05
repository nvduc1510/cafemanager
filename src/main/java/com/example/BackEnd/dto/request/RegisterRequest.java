package com.example.BackEnd.dto.request;

import com.example.BackEnd.entity.UserRole;
import lombok.Data;

import java.util.Date;
@Data
public class RegisterRequest {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String userFullName;
    private String userBirthdate;
    private String userAddress;
    private UserRole userRoleId;
}

