package com.lin.repository;

import com.lin.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    UserProfile findByEmail(String email);
    List<UserProfile> findAllByUsernameContains(String username);
    Integer deleteByEmail(String email);
}
