package com.example.BackEnd.dto;

import com.example.BackEnd.entity.Collections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer productId;
    private String productName;
    private Integer productPrice;
    private String productImage;
    private Integer collectionId;
    private String collectionName;
    private String collectionDescription;
}
