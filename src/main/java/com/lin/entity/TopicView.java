package com.lin.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "topic_views")
public class TopicView {

    @Id
    private String id;
    private String userId;
    private String topicId;
    private LocalDateTime viewedAt;

    // Constructors, getters, and setters
    public TopicView(String userId, String topicId, LocalDateTime viewedAt) {
        this.userId = userId;
        this.topicId = topicId;
        this.viewedAt = viewedAt;
    }

    // Getters and setters
}

