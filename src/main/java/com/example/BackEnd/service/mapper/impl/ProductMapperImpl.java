package com.example.BackEnd.service.mapper.impl;

import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.dto.UserDTO;
import com.example.BackEnd.entity.Product;
import com.example.BackEnd.entity.User;
import com.example.BackEnd.service.mapper.ProductMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("ProductMapperImpl")
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ListAllProductDTO entityToDto(Product entity) {
        ModelMapper modelMapper = new ModelMapper();
        ListAllProductDTO productDTO = modelMapper.map(entity, ListAllProductDTO.class);
        return productDTO;
    }

    @Override
    public Product dtoToEntity(ListAllProductDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(dto, Product.class);
        return product;
    }

    public List<ListAllProductDTO> pageToDtosList(Page<Product> entityPage) {
        return entityPage.getContent()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

}
