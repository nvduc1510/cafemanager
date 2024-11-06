package com.example.BackEnd.service;

import com.example.BackEnd.dto.response.ApiResponse;

public interface ProductService {
    ApiResponse listProduct(String productName, String ordProductName, Integer collectionId, int offset, int limit);
}
