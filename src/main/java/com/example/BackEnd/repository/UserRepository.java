package com.example.BackEnd.repository;

import com.example.BackEnd.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query("SELECT new com.example.BackEnd.entity.User(u.userId, u.username, " +
            "u.email, u.userFullName, " +
            "u.userSex, u.userBirthdate, " +
            "u.userAddress, u.userImage, " +
            "r.userRoleId, r.userRoleName) " +
            "FROM User u " +
            "JOIN  u.userRole r " +
            "WHERE u.userId = :userId " )
    User getUserById (@Param("userId") Integer userId);
//
    @Query("SELECT new com.example.BackEnd.entity.User(u.userId, u.username, " +
            "u.email, u.userFullName, " +
            "u.userSex, u.userBirthdate, " +
            "u.userAddress, u.userImage, " +
            "r.userRoleId, r.userRoleName) " +
            "FROM User u " +
            "JOIN  u.userRole r " +
            "WHERE (:userFullName IS NULL OR u.userFullName LIKE %:userFullName%) " )
    Page<User> listUser (@Param("userFullName") String userFullName, Pageable pageable);

    boolean existsByEmail(String email);
}