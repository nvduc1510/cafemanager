package com.example.BackEnd.service.mapper;

import com.example.BackEnd.dto.UserDTO;
import com.example.BackEnd.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
}