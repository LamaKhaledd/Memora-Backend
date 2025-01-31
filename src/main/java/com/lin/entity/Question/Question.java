package com.lin.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lin.entity.Flashcard;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "questions")
public class Question {
    @Id
    private String questionId;
    private boolean isSolved;  // true for solved, false for unsolved
    private String userId; // ID of the user who asked the question
    private String content; // The actual question content
    private List<String> tags; // Related tags/categories
    private Integer likesCount; // Number of likes for this question
    private Integer commentsCount; // Number of comments on this question
    private boolean isShared; // Flag for sharing status
    private List<String> likedByUsers; // List of user IDs who liked this question
    private String createdAt; // Timestamp of question creation
    private String updatedAt; // Timestamp of the last update
    private List<Comment> comments; // Comments on the question (instructor responses)
    private List<String> shares; // List of user IDs who shared the question
    private List<String> instructors;
    private List<Flashcard> flashcards;

}
