package com.lin.repository.Pomodoro;

import com.lin.entity.Pomodoro.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByFolderId(String folderId);
}
