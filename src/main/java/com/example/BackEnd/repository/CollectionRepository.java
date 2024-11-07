package com.example.BackEnd.repository;

import com.example.BackEnd.entity.Collections;
import com.example.BackEnd.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collections, Integer> {
    @Query("SELECT new com.example.BackEnd.entity.Collections(c.collectionId, c.collectionName, c.collectionDescription) " +
            "FROM Collections c " +
            "WHERE (:collectionName IS NULL OR c.collectionName LIKE %:collectionName%) ")
    Page<Collections> listCollections (@Param("collectionName") String collectionName,Pageable pageable);
}
