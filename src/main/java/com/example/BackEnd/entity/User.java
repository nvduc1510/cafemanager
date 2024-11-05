package com.example.BackEnd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",  unique = true, nullable = false)
    private Integer userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_sex")
    private String userSex;

    @Column(name = "user_birthdate")
    private String userBirthdate;

    @Column(name = "user_address", length = 125)
    private String userAddress;

    @Column(name = "user_image")
    private String userImage;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    public User(Integer userId, String username, String email, String userFullName,
                String userSex, String userBirthdate, String userAddress, String userImage, Integer userRoleId, String userRoleName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.userFullName = userFullName;
        this.userSex = userSex;
        this.userBirthdate = userBirthdate;
        this.userAddress = userAddress;
        this.userImage = userImage;
        this.userRole = new UserRole(userRoleId, userRoleName);
    }
}