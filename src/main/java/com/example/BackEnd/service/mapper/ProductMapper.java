package com.example.BackEnd.service.mapper;

import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ListAllProductDTO, Product> {
}