package com.example.BackEnd.service.impl;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.Collections;
import com.example.BackEnd.exception.InternalServerErrorException;
import com.example.BackEnd.exception.NotFoundException;
import com.example.BackEnd.repository.CollectionRepository;
import com.example.BackEnd.service.CollectionService;
import com.example.BackEnd.service.mapper.impl.CollectionMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CollectionMapperImpl collectionMapper;
    @Autowired
    private ApiResponse apiResponse;

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
    public ApiResponse getCollectionById(Integer categoryId) {
        return null;
    }

    @Override
    public ApiResponse updateCollection(CollectionDTO collectionDTO, Integer collectionId) {
        try {
            Optional<Collections> optionalCategory = collectionRepository.findById(collectionId);
            if (optionalCategory.isPresent()) {
                Collections collections = optionalCategory.get();
                collections = collectionMapper.dtoToEntity(collectionDTO);
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
        return null;
    }
}
