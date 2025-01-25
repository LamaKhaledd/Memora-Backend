package com.lin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lin.DTO.AdminRequest;

public interface AdminRequestRepository extends MongoRepository<AdminRequest, String> {
    AdminRequest findByEmail(String email);
}
