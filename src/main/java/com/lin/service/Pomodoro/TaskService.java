package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.Task;
import com.lin.repository.Pomodoro.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Create or Update Task
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all Tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get Tasks by User ID
    public List<Task> getTasksByUserId(String userId) {
        return taskRepository.findByUserId(userId);
    }

    // Get Task by ID
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }

    // Delete Task
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
}
