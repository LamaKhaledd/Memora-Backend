package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.Folder;
import com.lin.entity.Pomodoro.Project;
import com.lin.repository.Pomodoro.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ProjectService projectService;

    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    public Folder createFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    public void deleteFolder(String folderId) {
        List<Project> projects = projectService.getProjectsByFolderId(folderId);
        for (Project project : projects) {
            projectService.deleteProject(project.getId());
        }
        folderRepository.deleteById(folderId);
    }

    public Folder updateFolder(String folderId, Folder folder) {
        Optional<Folder> existingFolder = folderRepository.findById(folderId);
        if (existingFolder.isPresent()) {
            Folder updatedFolder = existingFolder.get();
            updatedFolder.setName(folder.getName());
            return folderRepository.save(updatedFolder);
        }
        return null;
    }
}
