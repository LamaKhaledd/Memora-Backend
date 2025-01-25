package com.lin.repository.Instructor;

import com.lin.entity.Instructor.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnnouncementRepository extends MongoRepository<Announcement, String> {
    List<Announcement> findByClassroomId(String classroomId);
}
