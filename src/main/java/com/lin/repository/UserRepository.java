package com.lin.repository;

import com.lin.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    void deleteByEmail(String email);
    //User findByUserId(String userId); you might need to use it

        Optional<User> findByUserId(String userId);

    List<User> findByUserIdIn(List<String> userIds);
    @NotNull
    Optional<User> findById(@NotNull String userId);  // Find user by userId
}
