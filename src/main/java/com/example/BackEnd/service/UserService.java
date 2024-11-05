package com.example.BackEnd.service;

import com.example.BackEnd.dto.UpdateUserDTO;
import com.example.BackEnd.dto.request.RegisterRequest;
import com.example.BackEnd.dto.response.ApiResponse;

public interface UserService {
    ApiResponse getUserByID(Integer userId);
    Boolean createUser(RegisterRequest request);
    Boolean forgetPassword(RegisterRequest request);
    ApiResponse listUser(String userFullName, String ordUserFullName, int offset, int limit);
    ApiResponse deleteUsers(Integer userId);
    ApiResponse updateUser(UpdateUserDTO userDTO, Integer userId);

}
