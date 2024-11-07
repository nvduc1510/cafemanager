package com.example.BackEnd.service;

import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.dto.ProductDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.Product;

public interface ProductService {
    ApiResponse listProduct(String productName, String ordProductName, Integer collectionId, int offset, int limit);
    ApiResponse getProductById(Integer productId);
    ApiResponse addProduct(ProductDTO productDTO);
    ApiResponse updateProduct(Integer productId, ListAllProductDTO productDTO);
    ApiResponse deleteProduct(Integer productId);
}
