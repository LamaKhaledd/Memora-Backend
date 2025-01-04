package com.lin.repository;

import com.lin.entity.UserFlashcardRelationship;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFlashcardRelationshipRepository extends MongoRepository<UserFlashcardRelationship, String> {

    List<UserFlashcardRelationship> findByUser_UserId(String userId);

    boolean existsByUser_UserIdAndFlashcard_Topic_Id(String userId, String id);

    boolean existsByUser_UserIdAndFlashcard_Id(String userId, String id);
}
