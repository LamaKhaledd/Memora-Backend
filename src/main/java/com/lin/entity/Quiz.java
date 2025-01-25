package com.lin.entity;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.DBRef;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quizzes")
public class Quiz {

    @Id
    private String id;

    private List<String> questions;
    private List<String> correctAnswers;
    private List<String> option1Controller;
    private List<String> option2Controller;
    private List<String> option3Controller;
    private List<String> option4Controller;
    private String quizName;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @DBRef
    private Topic topic;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<String> getOption1Controller() {
        return option1Controller;
    }

    public void setOption1Controller(List<String> option1Controller) {
        this.option1Controller = option1Controller;
    }

    public List<String> getOption2Controller() {
        return option2Controller;
    }

    public void setOption2Controller(List<String> option2Controller) {
        this.option2Controller = option2Controller;
    }

    public List<String> getOption3Controller() {
        return option3Controller;
    }

    public void setOption3Controller(List<String> option3Controller) {
        this.option3Controller = option3Controller;
    }

    public List<String> getOption4Controller() {
        return option4Controller;
    }

    public void setOption4Controller(List<String> option4Controller) {
        this.option4Controller = option4Controller;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
