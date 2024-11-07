package com.example.BackEnd.service.mapper;

import com.example.BackEnd.dto.ProductDTO;
import com.example.BackEnd.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper extends EntityMapper<ProductDTO, Product> {
}
