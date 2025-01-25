package com.lin.service.Pomodoro;
import com.lin.entity.Pomodoro.Project;
import com.lin.repository.Pomodoro.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getProjectsByFolderId(String folderId) {
        return projectRepository.findByFolderId(folderId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    public Project updateProject(String projectId, Project project) {
        Optional<Project> existingProject = projectRepository.findById(projectId);
        if (existingProject.isPresent()) {
            Project updatedProject = existingProject.get();
            updatedProject.setName(project.getName());
            updatedProject.setDescription(project.getDescription());
            return projectRepository.save(updatedProject);
        }
        return null;
    }
}
