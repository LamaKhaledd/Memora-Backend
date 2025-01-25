package com.lin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.lin.entity.Subject;
import com.lin.entity.User;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends MongoRepository<Subject, String> {
    List<Subject> findByUserId(String userId);
    Optional<Subject> findBySubjectNameIgnoreCase(String subjectName);
}
