package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "timers")
public class Timer {

    @Id
    private String id;
    private String userId; // Single timer per user
    private String timerMode;

    private long remainingTime = 0; // Remaining time in seconds
    private boolean isActive = false; // Whether the timer is currently active
    private Instant lastSwitchTime; // Last time the timer state was changed
    private boolean isPaused = false; // Whether the timer is currently active

    private boolean breakTimerFinished = false; // Flag for break timer completion
    private boolean pomodoroTimerFinished = false; // Flag for Pomodoro timer completion

    public Timer(String userId) {
        this.userId = userId;
        this.lastSwitchTime = Instant.now();
    }

    public Timer() {
        this.lastSwitchTime = Instant.now();
    }
}
