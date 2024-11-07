package com.example.BackEnd.controller;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.config.jwt.AuthUserDetails;
import com.example.BackEnd.dto.CollectionDTO;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.service.CollectionService;
import com.example.BackEnd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private UserService userService;

    @Autowired
    private ApiResponse apiResponse;

    @PostMapping("/add")
    public ResponseEntity<?> createCollections (@RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.ok(collectionService.createCollection(collectionDTO));
    }

    @PutMapping("/update/{collectionId}")
    public ResponseEntity<?> updateCollections (@RequestBody CollectionDTO collectionDTO,  @PathVariable Integer collectionId) {
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer currentRoleId = authUserDetails.getUser().getUserRole().getUserRoleId();
        if(currentRoleId.equals(1)) {
            return ResponseEntity.ok(collectionService.updateCollection(collectionDTO, collectionId));
        } else {
            apiResponse = new ApiResponse(Constants.NOT_ROLE);
            return ResponseEntity.ok(apiResponse);
        }

    }

    @DeleteMapping("/delete/{collectionId}")
    public ResponseEntity<?> deleteCollections (@PathVariable Integer collectionId) {
        AuthUserDetails authUserDetails = (AuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer currentRoleId = authUserDetails.getUser().getUserRole().getUserRoleId();
        if(currentRoleId.equals(1)) {
            return ResponseEntity.ok(collectionService.deleteCollection(collectionId));
        } else {
            apiResponse = new ApiResponse(Constants.NOT_ROLE);
            return ResponseEntity.ok(apiResponse);
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts(
            @RequestParam (required = false, defaultValue = "") String collectionName,
            @RequestParam (required = false, defaultValue = "asc") String ordCollectionName,
            @RequestParam( defaultValue = "0", required = false) int offset,
            @RequestParam( defaultValue = "5", required = false) int limit) {
        return ResponseEntity.ok(collectionService.getAllCollections(collectionName,ordCollectionName,offset, limit));

    }

    @GetMapping("/detail/{collectionId}")
    public ResponseEntity<?> getCollectionById (@PathVariable Integer collectionId) {
        return ResponseEntity.ok(collectionService.getCollectionById(collectionId));
    }

}
