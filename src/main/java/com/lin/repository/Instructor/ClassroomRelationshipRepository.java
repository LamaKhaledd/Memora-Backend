package com.lin.repository.Instructor;

import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassroomRelationshipRepository extends MongoRepository<ClassroomRelationship, String> {
    List<ClassroomRelationship> findByClassroomId(String classroomId); // Find relationships by classroomId
    List<ClassroomRelationship> findByStudentIdsContaining(String studentId); // Find relationships by student userId

    @Query("{'studentIds': ?0}")
    List<ClassroomRelationship> findAllByStudentId(String studentId);

    List<ClassroomRelationship> findAll(); // Find relationships by classroomId


    Optional<ClassroomRelationship> findByClassroomIdAndStudentIdsContaining(String classroomId, String studentId);

}
