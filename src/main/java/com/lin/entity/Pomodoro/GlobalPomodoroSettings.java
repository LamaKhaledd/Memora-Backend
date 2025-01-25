package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "global_pomodoro_settings")
public class GlobalPomodoroSettings {
    @Id
    private String id = "global"; // Single global document
    private int pomodoroLength = 25; // In minutes
    private int shortBreakLength = 5; // In minutes
    private int longBreakLength = 15; // In minutes
    private int longBreakAfter = 4; // Number of Pomodoro sessions before long break
    private boolean autoStartNextPomodoro = true;
    private boolean autoStartBreak = true;
    private boolean disableBreak = false;
}
