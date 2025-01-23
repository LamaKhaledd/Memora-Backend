package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_pomodoro_settings")
public class UserPomodoroSettings {
    @Id
    private String userId; // Tied to the specific user
    private Integer pomodoroLength; // Overrides global setting if not null
    private Integer shortBreakLength; // Overrides global setting if not null
    private Integer longBreakLength; // Overrides global setting if not null
    private Integer longBreakAfter; // Overrides global setting if not null
    private Boolean autoStartNextPomodoro; // Overrides global setting if not null
    private Boolean autoStartBreak; // Overrides global setting if not null
    private Boolean disableBreak; // Overrides global setting if not null


    public UserPomodoroSettings() {}


    // Constructor
    public UserPomodoroSettings(String userId) {
        this.userId = userId;
        this.pomodoroLength = 25; // default value
        this.shortBreakLength = 5; // default value
        this.longBreakLength = 15; // default value
    }


}
