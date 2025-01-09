package com.lin.controller.Pomodoro;

import com.lin.entity.Pomodoro.Folder;
import com.lin.service.Pomodoro.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    // Get all folders
    @GetMapping
    public List<Folder> getAllFolders() {
        return folderService.getAllFolders();
    }


    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        try {
            Folder newFolder = folderService.createFolder(folder);
            return new ResponseEntity<>(newFolder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Delete a folder and all associated projects
    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable String folderId) {
        folderService.deleteFolder(folderId);
    }

    // Update a folder
    @PutMapping("/{folderId}")
    public Folder updateFolder(@PathVariable String folderId, @RequestBody Folder folder) {
        return folderService.updateFolder(folderId, folder);
    }
}
