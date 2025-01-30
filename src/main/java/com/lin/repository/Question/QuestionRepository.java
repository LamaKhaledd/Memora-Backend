package com.lin.repository.Question;

import com.lin.entity.Question.Question;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
        List<Question> findByUserId(String userId);
        List<Question> findByTagsContaining(String tag);
}