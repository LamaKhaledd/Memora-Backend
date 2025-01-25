package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String name;
    private String folderId;
    private String description;
    private String createdAt;

    // Getters and Setters
}
