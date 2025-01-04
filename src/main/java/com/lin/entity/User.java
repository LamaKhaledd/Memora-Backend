
package com.lin.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
