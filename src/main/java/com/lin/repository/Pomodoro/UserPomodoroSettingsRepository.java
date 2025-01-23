package com.lin.repository.Pomodoro;


import com.lin.entity.Pomodoro.UserPomodoroSettings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPomodoroSettingsRepository extends MongoRepository<UserPomodoroSettings, String> {
    // You can add custom query methods if needed, for example:
    UserPomodoroSettings findByUserId(String userId);

}
