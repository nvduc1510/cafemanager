package com.example.BackEnd.dto;

import com.example.BackEnd.entity.Collections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAllProductDTO {
    private Integer productId;
    private String productName;
    private Integer productPrice;
    private String productImage;
    private Collections collections;
}
