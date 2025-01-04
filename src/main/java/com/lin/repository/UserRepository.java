package com.lin.repository;

import com.lin.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    void deleteByEmail(String email);
    User findByUserId(String userId);
}
