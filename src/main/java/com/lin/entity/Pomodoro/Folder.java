package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "folders")
public class Folder {
    @Id
    private String id;
    private String name;
    private List<String> projectIds;

    // Getters and Setters
}
