package com.lin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.lin.entity.Topic;
import com.lin.entity.Subject;

public interface TopicRepository extends MongoRepository<Topic, String> {
    List<Topic> findBySubject(Subject subject);

    List<Topic> findBySubjectAndUserId(Subject subject, String userId);

    
}

