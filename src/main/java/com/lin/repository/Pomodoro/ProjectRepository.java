package com.lin.repository.Pomodoro;

import com.lin.entity.Pomodoro.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByFolderId(String folderId);
}
