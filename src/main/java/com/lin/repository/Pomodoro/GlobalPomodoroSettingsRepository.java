package com.lin.repository.Pomodoro;


import com.lin.entity.Pomodoro.GlobalPomodoroSettings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalPomodoroSettingsRepository extends MongoRepository<GlobalPomodoroSettings, String> {
    // No additional methods needed for now, as we always retrieve/update the single global settings
  //  Optional<GlobalPomodoroSettings> findGlobalPomodoroSettings();
}
