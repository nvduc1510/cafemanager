package com.example.BackEnd.service;

import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.Collections;

public interface CollectionService {
    ApiResponse createCollection(CollectionDTO collectionDTO);
    ApiResponse getCollectionById(Integer categoryId);
    ApiResponse updateCollection(CollectionDTO collectionDTO, Integer collectionId);
    ApiResponse deleteCollection(Integer collectionId);

}
