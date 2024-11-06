package com.example.BackEnd.service.impl;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.Product;
import com.example.BackEnd.exception.NotFoundException;
import com.example.BackEnd.repository.ProductRepository;
import com.example.BackEnd.repository.UserRepository;
import com.example.BackEnd.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApiResponse apiResponse;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ApiResponse listProduct(String productName, String ordProductName, Integer collectionId, int offset, int limit) {
        try {
            List<ListAllProductDTO> products = new ArrayList<>();
            long total = 0;
            int page = offset / limit;
            String escapeGameName = productName != null ? productName
                    .replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_")
                    .replace(";", "\\;") : null;
            Sort sort = Sort.by(
                    "DESC".equalsIgnoreCase(ordProductName) ? Sort.Order.desc("productName") : Sort.Order.asc("productName"));
            Pageable pageable = PageRequest.of(page, limit, sort);
            Page<Product> productPage = productRepository.listProduct(productName,collectionId,pageable);
            total = productPage.getTotalElements();
            products = productPage.map(product -> {
                ListAllProductDTO productDTO = new ListAllProductDTO();
                modelMapper.map(product, productDTO);
                return productDTO;
            }).getContent();

            apiResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.GET_ALL_SUCCESS, total, products);
            return apiResponse;
        } catch (Exception ex) {
            throw new NotFoundException(Constants.GET_ALL_FALSE);
        }

    }
}
