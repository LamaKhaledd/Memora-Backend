package com.lin.repository;

import com.lin.entity.StudySession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudySessionRepository extends MongoRepository<StudySession, Integer> {
}
