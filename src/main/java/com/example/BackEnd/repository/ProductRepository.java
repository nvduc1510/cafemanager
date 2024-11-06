package com.example.BackEnd.repository;

import com.example.BackEnd.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT new com.example.BackEnd.entity.Product(p.productId, p.productName, p.productPrice, p.productImage," +
            " c.collectionId) " +
            "FROM Product p INNER JOIN p.collections c " +
            "WHERE (:productName IS NULL OR p.productName LIKE %:productName%) " +
            "AND (:collectionId IS NULL OR c.collectionId = :collectionId) "
    )
    Page<Product> listProduct (@Param("productName") String productName,
                               @Param("collectionId") Integer collectionId, Pageable pageable);
}
