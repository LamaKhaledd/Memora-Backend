package com.lin.controller.Pomodoro;

import com.lin.entity.Pomodoro.Project;
import com.lin.entity.Pomodoro.Task;
import com.lin.repository.Pomodoro.ProjectRepository;
import com.lin.service.Pomodoro.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    // Get all projects in a folder
    @GetMapping("/folder/{folderId}")
    public List<Project> getProjectsByFolderId(@PathVariable String folderId) {
        return projectService.getProjectsByFolderId(folderId);
    }






    @GetMapping("/all")
    public List<Project> getAllProjects() {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to the console
            throw new RuntimeException("Error fetching projects", e); // Throw an exception to propagate the error
        }
    }







    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            Project newProject = projectService.createProject(project);
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a project
    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
    }

    // Update a project
    @PutMapping("/{projectId}")
    public Project updateProject(@PathVariable String projectId, @RequestBody Project project) {
        return projectService.updateProject(projectId, project);
    }
}
