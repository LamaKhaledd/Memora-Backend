package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.GlobalPomodoroSettings;
import com.lin.repository.Pomodoro.GlobalPomodoroSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalPomodoroSettingsService {
    @Autowired
    private GlobalPomodoroSettingsRepository repository;



    // Fetch or initialize global settings
    public GlobalPomodoroSettings getGlobalSettings() {
        return repository.findById("global").orElseGet(() -> {
            // Create default global settings if not found
            GlobalPomodoroSettings defaultSettings = new GlobalPomodoroSettings();
            repository.save(defaultSettings); // Save for the first time
            return defaultSettings;
        });
    }


    public GlobalPomodoroSettings updateGlobalSettings(GlobalPomodoroSettings updatedSettings) {
        updatedSettings.setId("global");
        return repository.save(updatedSettings);
    }
}
