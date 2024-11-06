package com.example.BackEnd.service.mapper.impl;

import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.entity.Collections;
import com.example.BackEnd.service.mapper.CollectionMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("CollectionMapperImpl")
public class CollectionMapperImpl implements CollectionMapper {
    @Override
    public CollectionDTO entityToDto(Collections entity) {
        ModelMapper modelMapper = new ModelMapper();
        CollectionDTO collectionDTO = modelMapper.map(entity, CollectionDTO.class);
        return collectionDTO;
    }

    @Override
    public Collections dtoToEntity(CollectionDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Collections collections = modelMapper.map(dto, Collections.class);
        return collections;
    }

    public List<CollectionDTO> pageToDtosList(Page<Collections> entityPage) {
        return entityPage.getContent()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
