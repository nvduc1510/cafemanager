package com.example.BackEnd.controller;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.config.jwt.AuthUserDetails;
import com.example.BackEnd.dto.UpdateUserDTO;
import com.example.BackEnd.dto.UserDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.User;
import com.example.BackEnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApiResponse apiResponse;
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserByID(id));
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(
            @RequestParam( required = false, defaultValue = "") String userFullName,
            @RequestParam( required = false, defaultValue = "asc") String ordUserFullName,
            @RequestParam( defaultValue = "0", required = false) int offset,
            @RequestParam( defaultValue = "5", required = false) int limit) {
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer currentRoleId = authUserDetails.getUser().getUserRole().getUserRoleId();
        if(currentRoleId.equals(1)) {
            return ResponseEntity.ok(userService.listUser(userFullName, ordUserFullName, offset, limit));
        } else {
            apiResponse = new ApiResponse(Constants.NOT_ROLE);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser (@PathVariable Integer userId) {
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer currentRoleId = authUserDetails.getUser().getUserRole().getUserRoleId();
        Integer currentUserId =  authUserDetails.getUser().getUserId();
        if(currentRoleId.equals(1)) {
            return ResponseEntity.ok(userService.deleteUsers(userId));
        } else if(currentRoleId.equals(2)){
            return ResponseEntity.ok(userService.deleteUsers(currentUserId));
        } else {
            apiResponse = new ApiResponse(Constants.NOT_ROLE);
            return ResponseEntity.ok(apiResponse);
        }
    }
    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO userDTO, @PathVariable Integer userId) {
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer currentRoleId = authUserDetails.getUser().getUserRole().getUserRoleId();
        Integer currentUserId =  authUserDetails.getUser().getUserId();
        if(currentRoleId.equals(1)) {
            return ResponseEntity.ok(userService.updateUser(userDTO, userId));
        } else if (currentRoleId.equals(2)) {
            return ResponseEntity.ok(userService.updateUser(userDTO,currentUserId));
        } else {
            apiResponse = new ApiResponse(Constants.NOT_ROLE);
            return ResponseEntity.ok(apiResponse);
        }
    }
}