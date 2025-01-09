package com.lin.entity.Pomodoro;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
@Data
@Document(collection = "userStats")
public class UserStats {
    @Id
    private String userId;
    private long totalFocusTimeInSeconds;
    private int totalTasksCompleted;
    private Map<String, Long> projectTimeDistribution; // Project ID -> Time Spent in Seconds
    private int focusDaysGoal;
    private int focusDaysAchieved;
    private double goalCompletionRate;

    // Constructors, Getters, and Setters
}
