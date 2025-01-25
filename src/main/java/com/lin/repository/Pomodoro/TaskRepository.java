package com.lin.repository.Pomodoro;

import com.lin.entity.Pomodoro.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    // You can define custom query methods here if needed
    // For example:
    // List<Task> findByPriority(String priority);
    List<Task> findByUserId(String userId);
    Optional<Task> findByIdAndUserId(String taskId, String userId);

}
