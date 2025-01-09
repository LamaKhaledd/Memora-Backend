package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "timers")
public class Timer {
    @Id
    private String id;
    private String userId;
    private String taskId;
    private Instant startTime; // When the timer was started
    private Instant lastSwitchTime; // When the last task switch occurred
    private boolean isActive; // Whether the timer is currently active
    private boolean isBreakTime; // Whether it's break time
    private long remainingTime; // Remaining time in seconds
    private long breakDuration; // Break duration in seconds
    private Instant lastUpdatedTime; // Tracks when the timer was last updated
    private String lastKnownState; // Can store states like "Paused", "Active", etc.

    private Map<String, Long> taskDurations = new HashMap<>(); // Time spent per task (in seconds)

    public Timer() {
    }

    public Timer(String userId, String taskId, long pomodoroDuration, long breakDuration) {
        this.userId = userId;
        this.taskId = taskId;
        this.startTime = Instant.now();
        this.lastSwitchTime = startTime;
        this.isActive = true;
        this.isBreakTime = false;
        this.remainingTime = pomodoroDuration;
        this.breakDuration = breakDuration;
    }

    public void addTaskTime(String taskId, long elapsedTime) {
        taskDurations.put(taskId, taskDurations.getOrDefault(taskId, 0L) + elapsedTime);
    }


}
