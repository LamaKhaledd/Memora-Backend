package com.lin.repository.Pomodoro;


import com.lin.entity.Pomodoro.Timer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TimerRepository extends MongoRepository<Timer, String> {
    // Find all timers for a specific user
    List<Timer> findByUserId(String userId);

    // Find all timers for a specific task
    List<Timer> findByTaskId(String taskId);

    // Find all timers for a user that are not break times (focus sessions)
    List<Timer> findByUserIdAndIsBreakTimeFalse(String userId);

    // Find all timers for a user that are break times
    List<Timer> findByUserIdAndIsBreakTimeTrue(String userId);

    Optional<Timer> findActiveTimerByUserId(String userId);
    List<Timer> findByIsActive(boolean isActive);
    Timer findByUserIdAndActive(String userId, boolean active);
}
