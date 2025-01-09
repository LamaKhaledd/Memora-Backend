package com.lin.repository.Instructor;

import com.lin.entity.Instructor.ClassroomRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClassroomRelationshipRepository extends MongoRepository<ClassroomRelationship, String> {
    List<ClassroomRelationship> findByInstructorId(String instructorId); // Find relationships by instructor userId
    List<ClassroomRelationship> findByClassroomId(String classroomId); // Find relationships by classroomId
    List<ClassroomRelationship> findByStudentIdsContaining(String studentId); // Find relationships by student userId
}
