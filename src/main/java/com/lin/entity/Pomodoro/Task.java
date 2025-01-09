package com.lin.entity.Pomodoro;

//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
@Data
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String userId;

    //  @NotNull(message = "Task name cannot be null.")
    private String name;

    private String project = "Tasks"; // Default project
    private boolean completed = false;
    private int clockDuration = 25; // Default Pomodoro clock duration in minutes
    private int clocksRequired = 1; // Default Pomodoro clocks required
    private LocalDate dueDate = LocalDate.now(); // Default due date is today
    private String notes;
    private int pomodoroClockCount = 1; // Default Pomodoro clock count

    private LocalDate creationDate; // Immutable creation date

    // @Size(max = 10, message = "A task can have at most 10 subtasks.")
    private List<String> subtasks;

    private TaskPriority priority = TaskPriority.MEDIUM; // Default priority
    private String flagColor = "#4F3466"; // Default color for the flag (hex code)

    // Timer-related properties
    private boolean isTimerRunning = false;
    private int remainingTime = clockDuration * 60; // Time remaining in seconds
    private int completedCycles = 0; // Number of cycles completed

    // @Size(max = 10, message = "A task can have at most 10 tags.")
    private List<String> tags;

    // Constructor that initializes creationDate
    public Task(String name) {
        this.name = name;
        this.creationDate = LocalDate.now(); // Set the creation date here
    }

    // Default constructor if necessary
    public Task() {
        this.creationDate = LocalDate.now(); // Set the creation date here
    }

}
