package com.lin.entity.Pomodoro;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;
    private String userId;

    @NotNull(message = "Task name cannot be null.")
    private String name;

    private String project = "Tasks"; // Default project
    private boolean completed = false; // Default task completion flag
    private int numberOfRequiredPomodoros = 1; // Number of Pomodoros required for the task
    private int completedPomodoros = 0; // Completed Pomodoro cycles
    private LocalDate dueDate = LocalDate.now(); // Default due date is today
    private String notes; // Task-related notes

    private LocalDate creationDate = LocalDate.now(); // Immutable creation date
    private TaskPriority priority = TaskPriority.MEDIUM; // Default priority
    private String flagColor = "#4F3466"; // Default flag color (hex)

    private List<String> subtasks; // List of subtasks
    private List<Tag> tags; // Now using List<Tag> for consistency


    private boolean areCyclesCompleted = false; // Flag to indicate if all Pomodoro cycles are completed
    private long totalTimeSpentInSeconds = 0; // Total time spent in seconds

    public Task(String name) {
        this.name = name;
    }

    public Task() {
    }





}
