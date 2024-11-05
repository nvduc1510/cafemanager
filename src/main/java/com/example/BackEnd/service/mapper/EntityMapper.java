package com.example.BackEnd.service.mapper;

import com.example.BackEnd.dto.UserDTO;
import com.example.BackEnd.entity.User;
import org.mapstruct.Mapper;

public interface EntityMapper<D, E> {
    D entityToDto(E entity);
    E dtoToEntity(D dto);
}
