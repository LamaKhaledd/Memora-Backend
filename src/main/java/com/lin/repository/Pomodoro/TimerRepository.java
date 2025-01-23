package com.lin.repository.Pomodoro;


import com.lin.entity.Pomodoro.Timer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TimerRepository extends MongoRepository<Timer, String> {
   // Optional<Timer> findByUserIdAndTaskId(String userId, String taskId);
    Optional<Timer> findByUserId(String userId);

}
