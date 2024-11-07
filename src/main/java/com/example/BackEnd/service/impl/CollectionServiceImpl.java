package com.example.BackEnd.service.impl;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.dto.ListAllProductDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.Collections;
import com.example.BackEnd.entity.Product;
import com.example.BackEnd.exception.InternalServerErrorException;
import com.example.BackEnd.exception.NotFoundException;
import com.example.BackEnd.repository.CollectionRepository;
import com.example.BackEnd.service.CollectionService;
import com.example.BackEnd.service.mapper.impl.CollectionMapperImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CollectionMapperImpl collectionMapper;
    @Autowired
    private ApiResponse apiResponse;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ApiResponse createCollection(CollectionDTO collectionDTO) {
        try {
            Collections collection = collectionMapper.dtoToEntity(collectionDTO);
            Collections collections = collectionRepository.save(collection);
            return new ApiResponse(HttpServletResponse.SC_OK, Constants.CREATE_SUCCESS, collections);
        } catch (Exception e) {
            return new ApiResponse(HttpServletResponse.SC_BAD_REQUEST, Constants.CREATE_FALSE, null);
        }
    }

    @Override
    public ApiResponse getAllCollections(String collectionName, String ordCollectionName, int offset, int limit) {
        try {
            List<CollectionDTO> collectionDTOS = new ArrayList<>();
            long total = 0;
            int page = offset / limit;
            String escapeGameName = collectionName != null ? collectionName
                    .replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_")
                    .replace(";", "\\;") : null;
            Sort sort = Sort.by(
                    "DESC".equalsIgnoreCase(ordCollectionName) ? Sort.Order.desc("collectionName") : Sort.Order.asc("collectionName"));
            Pageable pageable = PageRequest.of(page, limit, sort);
            Page<Collections> collectionsPage = collectionRepository.listCollections(collectionName,pageable);
            total = collectionsPage.getTotalElements();
            collectionDTOS = collectionsPage.map(collections -> {
                CollectionDTO collectionDTO = new CollectionDTO();
                modelMapper.map(collections, collectionDTO);
                return collectionDTO;
            }).getContent();

            apiResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.GET_ALL_SUCCESS, total, collectionDTOS);
            return apiResponse;
        } catch (Exception ex) {
            throw new NotFoundException(Constants.GET_ALL_FALSE);
        }
    }

    @Override
    public ApiResponse getCollectionById(Integer categoryId) {
        return null;
    }

    @Override
    public ApiResponse updateCollection(CollectionDTO collectionDTO, Integer collectionId) {
        try {
            Optional<Collections> optionalCollections = collectionRepository.findById(collectionId);
            if (optionalCollections.isPresent()) {
                Collections collections = optionalCollections.get();
                collections.setCollectionName(collectionDTO.getCollectionName());
                collections.setCollectionDescription(collectionDTO.getCollectionDescription());
                collectionRepository.save(collections);
                apiResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.UPDATE_SUCCESS + collectionId, collectionDTO);
                return apiResponse;
            } else {
                throw new NotFoundException(Constants.GET_FALSE);
            }
        } catch (InternalServerErrorException ex) {
            throw new InternalServerErrorException(Constants.UPDATE_FAlSE);
        }
    }

    @Override
    public ApiResponse deleteCollection(Integer collectionId) {
        try {
            Optional<Collections> optionalCollections = collectionRepository.findById(collectionId);
            if(optionalCollections.isPresent()) {
                collectionRepository.delete(optionalCollections.get());
                apiResponse = new ApiResponse(HttpServletResponse.SC_OK,Constants.DELETE_SUCCESS + collectionId,collectionId);
                return apiResponse;
            } else {
                throw new NotFoundException(Constants.GET_FALSE);
            }
        } catch (InternalServerErrorException ex) {
            throw new InternalServerErrorException(Constants.DELETE_FALSE);
        }
    }
}
