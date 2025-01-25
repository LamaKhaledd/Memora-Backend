package com.lin.repository.Pomodoro;

import com.lin.entity.Pomodoro.PomodoroSessions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroSessionsRepository extends MongoRepository<PomodoroSessions, String> {
    List<PomodoroSessions> findByTaskIdAndCompleted(String taskId, boolean isCompleted);
   // Optional<PomodoroSessions> save(PomodoroSessions pomodoroSession);
}

