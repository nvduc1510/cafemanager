package com.example.BackEnd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<com.example.BackEnd.entity.UserRole, Integer> {
}
