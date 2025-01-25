package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.Tag;
import com.lin.entity.Pomodoro.Task;
import com.lin.entity.Pomodoro.TaskPriority;
import com.lin.repository.Pomodoro.TagRepository;
import com.lin.repository.Pomodoro.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private TagRepository tagRepository;

    // Create or Update Task
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all Tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Delete Task
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }


    // Method to mark cycles as completed
    public void markCyclesAsCompleted(String taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setAreCyclesCompleted(true);
        taskRepository.save(task);
    }

    // Method to update task time tracking
    public void updateTaskTimeSpent(String taskId, long timeSpent) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTotalTimeSpentInSeconds(task.getTotalTimeSpentInSeconds() + timeSpent);
        taskRepository.save(task);
    }

    // Method to update the task's Pomodoro cycle count
    public void updatePomodoroCycles(String taskId, int cyclesCompleted) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompletedPomodoros(cyclesCompleted);
        if (task.getCompletedPomodoros() >= task.getNumberOfRequiredPomodoros()) {
            task.setAreCyclesCompleted(true);
        }
        taskRepository.save(task);
    }



    public Task updateTask(String taskId, Task updatedFields) {
        // Fetch the existing task from the database
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        // Update fields dynamically if provided
        if (updatedFields.getName() != null) {
            existingTask.setName(updatedFields.getName());
        }
        if (updatedFields.getProject() != null) {
            existingTask.setProject(updatedFields.getProject());
        }
        if (updatedFields.getDueDate() != null) {
            existingTask.setDueDate(updatedFields.getDueDate());
        }
        if (updatedFields.getNotes() != null) {
            existingTask.setNotes(updatedFields.getNotes());
        }
        if (updatedFields.getPriority() != null) {
            existingTask.setPriority(updatedFields.getPriority());
        }
        if (updatedFields.getFlagColor() != null) {
            existingTask.setFlagColor(updatedFields.getFlagColor());
        }
        if (updatedFields.getTags() != null) {
            existingTask.setTags(updatedFields.getTags());
        }
        if (updatedFields.getSubtasks() != null) {
            existingTask.setSubtasks(updatedFields.getSubtasks());
        }

        if (updatedFields.getCompletedPomodoros() != 0) {
            existingTask.setCompletedPomodoros(updatedFields.getCompletedPomodoros());
        }

        if (updatedFields.isAreCyclesCompleted()) {
            existingTask.setAreCyclesCompleted(updatedFields.isAreCyclesCompleted());
        }

        // Save the updated task back to the repository
        return taskRepository.save(existingTask);
    }

    public Task getTaskById(String taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }



    // Remove tag from a task
    public void removeTagFromTask(String taskId, String tagId) throws RuntimeException, RuntimeException {
        // Retrieve the task by ID
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Retrieve the tag by ID
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // Remove the tag from the task's tags list
        task.getTags().remove(tag);

        // Save the updated task back to the database
        taskRepository.save(task);
    }


    public static TaskPriority stringToPriority(String priorityString) {
        try {
            return TaskPriority.valueOf(priorityString.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid input gracefully (e.g., return a default value)
            return TaskPriority.NONE;
        }
    }



    public void toggleTaskCompleted(String taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);    }
}
