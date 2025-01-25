package com.lin.repository.Instructor;

import com.lin.entity.Instructor.Classroom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClassroomRepository extends MongoRepository<Classroom, String> {
    List<Classroom> findByInstructorId(String instructorId);
    Classroom findByClassroomId(String classroomId);


}
