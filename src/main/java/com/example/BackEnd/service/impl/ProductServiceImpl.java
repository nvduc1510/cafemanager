package com.example.BackEnd.service.impl;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.dto.ProductDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.Collections;
import com.example.BackEnd.entity.Product;
import com.example.BackEnd.exception.NotFoundException;
import com.example.BackEnd.repository.CollectionRepository;
import com.example.BackEnd.repository.ProductRepository;
import com.example.BackEnd.repository.UserRepository;
import com.example.BackEnd.service.ProductService;
import com.example.BackEnd.service.mapper.ProductDTOMapper;
import com.example.BackEnd.service.mapper.ProductMapper;
import com.example.BackEnd.service.mapper.impl.ProductDTOMapperImpl;
import com.example.BackEnd.service.mapper.impl.ProductMapperImpl;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ProductMapperImpl productMapper;

    @Autowired
    private ApiResponse apiResponse;

    @Autowired
    private ProductDTOMapperImpl productDTOMapper;

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

    @Override
    public ApiResponse getProductById(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        ListAllProductDTO productDTO = new ListAllProductDTO();
        if(product.isPresent()) {
            productDTO = productMapper.entityToDto(product.get());
            apiResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.SUCCESS, productDTO);
        } else {
            apiResponse = new ApiResponse(HttpServletResponse.SC_BAD_GATEWAY, Constants.GET_FALSE, productDTO);
        }
        return apiResponse;
    }

    @Override
    @Transactional
    public ApiResponse addProduct(ProductDTO productDTO) {
        if(productDTO.getProductId() != null && productRepository.findById(productDTO.getProductId()).isPresent()) {
            apiResponse = new ApiResponse(Constants.VALID_PRODUCT);
        }else {
            Product product = new Product();
            if (productDTO.getCollectionId() != null) {
                Optional<Collections> collections = collectionRepository.findById(productDTO.getCollectionId());
                if(collections.isPresent()) {
                    product = productDTOMapper.dtoToEntity(productDTO);
                    product.setCollections(collections.get());
                } else {
                    return new ApiResponse(HttpServletResponse.SC_BAD_REQUEST, "Collection not found", null);
                }
            } else {
                Collections newCollection = new Collections();
                newCollection.setCollectionName(productDTO.getCollectionName());
                newCollection.setCollectionDescription(productDTO.getCollectionDescription());
                collectionRepository.save(newCollection);
                product = productDTOMapper.dtoToEntity(productDTO);
                product.setCollections(newCollection);
            }
            productRepository.save(product);
            apiResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.CREATE_SUCCESS, productDTO);
        }

        return apiResponse;
    }

    @Override
    public ApiResponse updateProduct(Integer productId, ListAllProductDTO productDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteProduct(Integer productId) {
        return null;
    }
}
