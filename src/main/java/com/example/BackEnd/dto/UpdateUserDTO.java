package com.example.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String userFullName;
    private String userSex;
    private String userBirthdate;
    private String userAddress;
    private String userImage;
}
