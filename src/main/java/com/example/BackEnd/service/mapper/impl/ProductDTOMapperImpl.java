package com.example.BackEnd.service.mapper.impl;

import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.dto.ProductDTO;
import com.example.BackEnd.entity.Product;
import com.example.BackEnd.service.mapper.ProductDTOMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("ProductDTOMapper")
public class ProductDTOMapperImpl implements ProductDTOMapper {


    @Override
    public ProductDTO entityToDto(Product entity) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO productDTO = modelMapper.map(entity, ProductDTO.class);
        return productDTO;
    }

    @Override
    public Product dtoToEntity(ProductDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(dto, Product.class);
        return product;
    }


    public List<ProductDTO> pageToDtosList(Page<Product> entityPage) {
        return entityPage.getContent()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
