package com.lin.repository;

import com.lin.entity.ConfirmToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmToken, String> {
    ConfirmToken findByUserId(String userId);
}
