package com.lin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "study_sessions")
public class StudySession {

    @Id
    private String id; // Use String for MongoDB ObjectId

    private String subjectId; // Changed to String
    private String topicName;
    private String topicId; // Changed to String
    private int correctAnswerCount;
    private int incorrectAnswerCount;
    private int totalQuestions;
    private String totalTimeSpent;
    private int easyQuestionCount;
    private int mediumQuestionCount;
    private int hardQuestionCount;

    @DBRef
    private List<Flashcard> easyQuestions;

    @DBRef
    private List<Flashcard> mediumQuestions;

    @DBRef
    private List<Flashcard> hardQuestions;

    private LocalDateTime createdAt;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(int correctAnswerCount) {
        this.correctAnswerCount = correctAnswerCount;
    }

    public int getIncorrectAnswerCount() {
        return incorrectAnswerCount;
    }

    public void setIncorrectAnswerCount(int incorrectAnswerCount) {
        this.incorrectAnswerCount = incorrectAnswerCount;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(String totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public int getEasyQuestionCount() {
        return easyQuestionCount;
    }

    public void setEasyQuestionCount(int easyQuestionCount) {
        this.easyQuestionCount = easyQuestionCount;
    }

    public int getMediumQuestionCount() {
        return mediumQuestionCount;
    }

    public void setMediumQuestionCount(int mediumQuestionCount) {
        this.mediumQuestionCount = mediumQuestionCount;
    }

    public int getHardQuestionCount() {
        return hardQuestionCount;
    }

    public void setHardQuestionCount(int hardQuestionCount) {
        this.hardQuestionCount = hardQuestionCount;
    }

    public List<Flashcard> getEasyQuestions() {
        return easyQuestions;
    }

    public void setEasyQuestions(List<Flashcard> easyQuestions) {
        this.easyQuestions = easyQuestions;
    }

    public List<Flashcard> getMediumQuestions() {
        return mediumQuestions;
    }

    public void setMediumQuestions(List<Flashcard> mediumQuestions) {
        this.mediumQuestions = mediumQuestions;
    }

    public List<Flashcard> getHardQuestions() {
        return hardQuestions;
    }

    public void setHardQuestions(List<Flashcard> hardQuestions) {
        this.hardQuestions = hardQuestions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
