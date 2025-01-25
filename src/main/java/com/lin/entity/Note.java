package com.lin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String title;      // Changed from text to title
    private String subTitle;   // New field
    private String date;       // New field
    private long color;
    private boolean isFavourite;
    private String userId;     // Reference to the associated User

    // Additional methods for JSON serialization and deserialization can be added if needed
}
