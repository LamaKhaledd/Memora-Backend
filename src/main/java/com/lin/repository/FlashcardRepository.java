package com.lin.repository;

import com.lin.entity.Flashcard;
import com.lin.entity.Topic;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends MongoRepository<Flashcard, String> {
    // Add this method to query by topicId
    List<Flashcard> findByTopicId(String topicId);

    // Add this method to query by subjectId if needed
    List<Flashcard> findBySubjectId(String subjectId);

    List<Flashcard> findByTopic(Topic topic);

    List<Flashcard> findByTopic(Optional<Topic> topic);
}
