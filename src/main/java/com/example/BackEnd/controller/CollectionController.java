package com.example.BackEnd.controller;

import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/add")
    public ResponseEntity<?> createCollections (@RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.ok(collectionService.createCollection(collectionDTO));
    }

}
