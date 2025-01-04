package com.lin.repository;

import com.lin.entity.ParentChildRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ParentChildRelationshipRepository extends MongoRepository<ParentChildRelationship, String> {
    Optional<ParentChildRelationship> findByParentId(String parentId);
}
