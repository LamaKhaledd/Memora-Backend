package com.lin.controller.Pomodoro;

import com.lin.entity.Pomodoro.Task;
import com.lin.repository.Pomodoro.TaskRepository;
import com.lin.service.Pomodoro.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error to the console
            throw new RuntimeException("Error fetching tasks", e); // Throw an exception to propagate the error
        }
    }

    // Get tasks by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable String userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    // Get a task by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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


    // Update a task by its ID
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

            // Update fields here (assuming we just overwrite the entire task object for simplicity)
            existingTask.setName(updatedTask.getName());  // Example field update, add other fields as needed

            Task savedTask = taskRepository.save(existingTask);  // Save updated task to DB
            return ResponseEntity.ok(savedTask);
        } else {
            return ResponseEntity.notFound().build();  // Task not found
        }
    }




    // Toggle task completion status by task ID
    @PatchMapping("/{id}/toggle-completion")
    public ResponseEntity<Task> toggleTaskCompletion(@PathVariable String id) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();

            // Toggle the completion status
            existingTask.setCompleted(!existingTask.isCompleted());

            Task savedTask = taskRepository.save(existingTask);  // Save updated task to DB
            return ResponseEntity.ok(savedTask);  // Return the updated task
        } else {
            return ResponseEntity.notFound().build();  // Task not found
        }
    }


    

}
