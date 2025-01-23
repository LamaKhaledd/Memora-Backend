package com.lin.controller.Pomodoro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.entity.Pomodoro.Tag;
import com.lin.entity.Pomodoro.Task;
import com.lin.entity.Pomodoro.TaskPriority;
import com.lin.repository.Pomodoro.TaskRepository;
import com.lin.service.Pomodoro.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;


    // Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        System.out.println("Received Task: " + task); // Log the task received

        // Save task to DB
        Task savedTask = taskRepository.save(task);
        System.out.println("Saved Task: " + savedTask); // Log the saved task

        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public List<Task> getAllTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to the console
            throw new RuntimeException("Error fetching tasks", e); // Throw an exception to propagate the error
        }
    }

    @GetMapping("/all/{userId}")
    public List<Task> getTasksByUserId(@PathVariable String userId) {
        try {
            return taskRepository.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to the console
            throw new RuntimeException("Error fetching tasks", e); // Throw an exception to propagate the error
        }
    }



    @GetMapping("/task/{taskId}")
    public Optional<Task> getTasksById(@PathVariable String taskId) {
        try {
            return taskRepository.findById(taskId);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to the console
            throw new RuntimeException("Error fetching tasks", e); // Throw an exception to propagate the error
        }
    }

    // Delete a task by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    ////////////////////////////////////


    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable String taskId, @RequestBody Task updatedFields) {
        return taskService.updateTask(taskId, updatedFields);
    }
/*
    @PutMapping("/complete/{taskId}")
    public void markCyclesAsCompleted(@PathVariable String taskId) {
        taskService.markCyclesAsCompleted(taskId);
    }*/
@PutMapping("/completed/{taskId}")
public ResponseEntity<Map<String, Object>> markTaskAsCompleted(@PathVariable String taskId) {
    taskService.toggleTaskCompleted(taskId);

    // Create a response with updated task info or status
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "Task completion toggled successfully");

    return ResponseEntity.ok(response);
}


    /////////////////////////

    @PutMapping("/{id}/notes")
    public ResponseEntity<Task> updateTaskNotes(@PathVariable String id, @RequestBody Map<String, String> body) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        String notes = body.get("notes");  // Extracting the "notes" field from the request body


        task.setNotes(notes);
        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("tasks/get/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable String taskId) {
        try {
            Task task = taskService.getTaskById(taskId);
            if (task != null) {
               // System.out.println("Fetched Task: " + task); // Log the fetched task
                return ResponseEntity.ok(task);
            } else {
              //  System.out.println("Task not found with ID: " + taskId); // Log not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            System.err.println("Error fetching task: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/tasks/{id}/due-date")
    public ResponseEntity<Task> updateTaskDueDate(@PathVariable String id, @RequestBody String dueDateString) {
        System.out.println("Received dueDate: " + dueDateString);

        try {
            // Extract dueDate from the JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> dueDateMap = objectMapper.readValue(dueDateString, Map.class);
            String dueDateStr = (String) dueDateMap.get("dueDate");

            // Parse the received date and add 1 day
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dueDateStr);
            LocalDate parsedDate = offsetDateTime.plusDays(1).toLocalDate(); // Add 1 day

            // Retrieve and update the task
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            task.setDueDate(parsedDate);

            Task updatedTask = taskRepository.save(task);

            return ResponseEntity.ok(updatedTask);
        } catch (JsonProcessingException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (DateTimeParseException e) {
            System.out.println("Date parsing error: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/tasks/{id}/clocks-required")
    public ResponseEntity<Task> updateClocksRequired(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        if (updates.containsKey("clocksRequired")) { // Adjusted key
            task.setNumberOfRequiredPomodoros((Integer) updates.get("clocksRequired"));
        }

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }
    @PutMapping("/{taskId}/priority")
    public Task updateTaskPriority(@PathVariable String taskId, @RequestBody String priority) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        TaskPriority taskPriority = stringToPriority(priority); // Convert string to enum
        task.setPriority(taskPriority);
        updateFlagColorBasedOnPriority(task,taskPriority);

        return taskRepository.save(task);
    }

    // Helper method to convert string to TaskPriority enum
    private TaskPriority stringToPriority(String priorityString) {
        try {            System.out.println("Date parsing error: " + priorityString); // Debug parsing error

            return switch (priorityString) {
                case "{\"priority\":\"MEDIUM\"}" -> TaskPriority.MEDIUM;
                case "{\"priority\":\"LOW\"}" -> TaskPriority.LOW;
                case "{\"priority\":\"HIGH\"}" -> TaskPriority.HIGH;
                case "{\"priority\":\"NONE\"}" -> TaskPriority.NONE;
                default -> TaskPriority.valueOf(priorityString.toUpperCase());
            };


        } catch (IllegalArgumentException e) {
            // Handle invalid input gracefully (e.g., return a default value)
            return TaskPriority.NONE;
        }
    }


    // Method to update flag color based on the priority
    public void updateFlagColorBasedOnPriority(Task task,TaskPriority priority) {
        switch (priority) {

            case HIGH:
                 task.setFlagColor("#FF0000"); //red
                break;
            case MEDIUM:
                task.setFlagColor("#FFA500"); // Orange
                break;
            case LOW:
                task.setFlagColor("#008000"); // Green
                break;
            case NONE:
                task.setFlagColor("#808080"); // Grey
                break;
        }
    }
    @PutMapping("/{taskId}/addTag")
    public ResponseEntity<?> addTagToTask(@PathVariable String taskId, @RequestBody Tag tagRequest) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        // Ensure the tagName is not empty or null
        if (tagRequest.getName() == null || tagRequest.getName().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag name cannot be empty.");
        }

        Tag tag = new Tag(tagRequest.getName(), tagRequest.getColor());  // Assuming Tag has a constructor for color as well
        if (!task.getTags().contains(tag)) {
            task.getTags().add(tag); // Add the tag if it doesn't already exist
            taskRepository.save(task);
            return ResponseEntity.ok("Tag added successfully.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Tag already exists.");
    }

    // Endpoint to remove a tag from a task
    @DeleteMapping("/{taskId}/tags/{tagId}")
    public ResponseEntity<Void> removeTagFromTask(@PathVariable String taskId, @PathVariable String tagId) {
        try {
            taskService.removeTagFromTask(taskId, tagId);
            return ResponseEntity.noContent().build(); // Successfully removed tag
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Task or Tag not found
        }
    }


}
