package com.lin.entity.Pomodoro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "pomodoro_sessions")
public class PomodoroSessions {

    @Id
    private String sessionId;
    private String userId;
    private String taskId; // Associated task ID
    private Instant startTime;

    private Instant endTime; // End time in epoch seconds
    private boolean isCompleted = false;
    private long totalStudyTimeInSeconds = 0; // Total study time for the session
    private boolean breakStarted = false;

    public PomodoroSessions(String userId, String taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public PomodoroSessions() {
    }
}
