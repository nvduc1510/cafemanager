package com.example.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDTO {
    private Integer collectionId;
    private String collectionName;
    private String collectionDescription;
}
