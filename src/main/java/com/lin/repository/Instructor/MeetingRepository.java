package com.lin.repository.Instructor;

import com.lin.entity.Instructor.Meeting;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MeetingRepository extends MongoRepository<Meeting, String> {
    List<Meeting> findByClassroomId(String classroomId);
    List<Meeting> findByStatus(String status);
}
