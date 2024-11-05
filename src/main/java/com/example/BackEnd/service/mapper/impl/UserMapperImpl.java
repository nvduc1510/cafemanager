package com.example.BackEnd.service.mapper.impl;

import com.example.BackEnd.dto.UserDTO;
import com.example.BackEnd.entity.User;
import com.example.BackEnd.service.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("UserMapperImpl")
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDTO entityToDto(User entity) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);
        return userDTO;
    }

    @Override
    public User dtoToEntity(UserDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(dto, User.class);
        return user;
    }
    public List<UserDTO> pageToDtosList(Page<User> entityPage) {
        return entityPage.getContent()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<User> pageToEntitiesList(Page<UserDTO> dtoPage) {
        return dtoPage.getContent()
                .stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
