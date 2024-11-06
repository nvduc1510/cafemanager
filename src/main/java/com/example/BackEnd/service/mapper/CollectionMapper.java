package com.example.BackEnd.service.mapper;

import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.entity.Collections;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollectionMapper extends EntityMapper<CollectionDTO, Collections> {
}
