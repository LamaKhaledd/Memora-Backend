package com.lin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String email;
    private String password;
    private String about;
    private String image;
    private String location;
    private String created_at;
    private String last_active;
    private boolean isOnline;
    private String telephone;
    private String favouriteCategories;
    private Integer age;
    private Integer numOfCreatedFlashcards;
    private Integer numOfCompletedFlashcards;
    private Integer studyStreak;
    private String username;
    private boolean isEnabled;
    private UserRole role;
    private Integer flashcardsCount;

    // Nullable fields for instructor-specific data
    private List<String> classroomsJoined; // Nullable for users
    private List<String> classroomsCreated; // Nullable for instructors
    private List<String> reminders; // Nullable for instructors

}
