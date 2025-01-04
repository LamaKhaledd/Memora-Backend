package com.lin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "topics")
@JsonPropertyOrder({"id","userId", "subjectId", "imageUrl", "topicName", "flashcards"})
public class Topic {

    @Id
    private String id;
    private String userId; 
    private String topicName;
    private String imageUrl;

    @DBRef
    @JsonBackReference
    private Subject subject;

    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subject != null ? subject.getId() : null;
    }

    @DBRef
    private List<Flashcard> flashcards;  
}
