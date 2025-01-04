package com.lin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_flashcard_relationships")
public class UserFlashcardRelationship {

    @Id
    private String id;

    @DBRef
    private User user;   

    @DBRef
    private Flashcard flashcard; 
    private RelationshipType relationshipType;
}
