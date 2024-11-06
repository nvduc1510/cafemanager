package com.example.BackEnd.controller;

import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ApiResponse apiResponse;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts(
            @RequestParam (required = false, defaultValue = "") String productName,
            @RequestParam (required = false, defaultValue = "") Integer collectionId,
            @RequestParam (required = false, defaultValue = "asc") String ordProductName,
            @RequestParam( defaultValue = "0", required = false) int offset,
            @RequestParam( defaultValue = "5", required = false) int limit) {
        return ResponseEntity.ok(productService.listProduct(productName,ordProductName,collectionId,offset, limit));

    }
}
