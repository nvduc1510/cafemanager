package com.example.BackEnd.dto;
import com.example.BackEnd.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private String userFullName;
    private String userSex;
    private String userBirthdate;
    private String userAddress;
    private Integer userRoleId;
    private String userRoleName;
    private String userImage;
}