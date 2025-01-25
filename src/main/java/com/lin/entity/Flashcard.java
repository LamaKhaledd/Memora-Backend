package com.lin.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "flashcards")
@JsonPropertyOrder({"id", "flag", "subjectId", "topicId", "userId","question", "answer", "difficulty", "lastResponse", "imageUrl", "visibility", "nextReviewDate", "interval", "easinessFactor"})
public class Flashcard {

    @Id
    private String id; 
    private String flag;
    private String question;
    private String answer;
    private Integer difficulty; 
    private Boolean lastResponse;
    private String imageUrl;
    private FlashcardVisibility visibility; 
    

    private LocalDateTime nextReviewDate = LocalDateTime.now(); 
    private Integer interval = 1; // Default to 1 day
    private Double easinessFactor = 2.5;

    
    @DBRef
    @JsonIgnore
    private Subject subject;

    @DBRef
    @JsonIgnore
    private Topic topic;

    @DBRef
    @JsonIgnore
    private User user;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(Boolean lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject2) {
        this.subject = subject2;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }



    // Getter and Setter for nextReviewDate
    public LocalDateTime getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(LocalDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    // Getter and Setter for interval
    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }


    // Getter and Setter for easinessFactor
    public Double getEasinessFactor() {
        return easinessFactor;
    }

    public void setEasinessFactor(Double easinessFactor) {
        this.easinessFactor = easinessFactor;
    }
}
