package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.GlobalPomodoroSettings;
import com.lin.entity.Pomodoro.UserPomodoroSettings;
import com.lin.repository.Pomodoro.GlobalPomodoroSettingsRepository;
import com.lin.repository.Pomodoro.UserPomodoroSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PomodoroSettingsService {

    @Autowired
    private GlobalPomodoroSettingsRepository globalSettingsRepo;

    @Autowired
    private UserPomodoroSettingsRepository userSettingsRepo;


    public GlobalPomodoroSettings getGlobalSettings() {
        // Attempt to find global settings from the database
        Optional<GlobalPomodoroSettings> globalSettingsOpt = globalSettingsRepo.findById("default");

        if (globalSettingsOpt.isEmpty()) {
            // If settings don't exist, create and save them
            GlobalPomodoroSettings defaultSettings = new GlobalPomodoroSettings();  // Defaults are already initialized in the entity
            globalSettingsRepo.save(defaultSettings);
            return defaultSettings;
        } else {
            // If settings exist, return them
            return globalSettingsOpt.get();
        }
    }


    public void updateUserSettings(UserPomodoroSettings settings) {
        // Check if the user already has settings
        UserPomodoroSettings existingSettings = userSettingsRepo.findByUserId(settings.getUserId());

        if (existingSettings != null) {
            // If settings exist, update them
            existingSettings.setPomodoroLength(settings.getPomodoroLength());
            existingSettings.setShortBreakLength(settings.getShortBreakLength());
            existingSettings.setLongBreakLength(settings.getLongBreakLength());
            existingSettings.setLongBreakAfter(settings.getLongBreakAfter());
            existingSettings.setAutoStartNextPomodoro(settings.getAutoStartNextPomodoro());
            existingSettings.setAutoStartBreak(settings.getAutoStartBreak());
            existingSettings.setDisableBreak(settings.getDisableBreak());

            // Save the updated settings
            userSettingsRepo.save(existingSettings);
        } else {
            // If settings don't exist, create new settings for the user
            userSettingsRepo.save(settings);
        }
    }


    public UserPomodoroSettings getUserSettings(String userId) {
        UserPomodoroSettings userSettings = userSettingsRepo.findByUserId(userId);
        if(userSettings == null) {
            return new UserPomodoroSettings(userId); // create new settings for the user if not found
        }
        return userSettings;
    }



    public void updateGlobalSettings(GlobalPomodoroSettings settings) {
        globalSettingsRepo.save(settings);
    }



    public int getPomodoroLengthForUser(String userId) {
        UserPomodoroSettings userSettings = getUserSettings(userId);
        return userSettings.getPomodoroLength() > 0 ? userSettings.getPomodoroLength() : getGlobalSettings().getPomodoroLength();
    }

    public int getBreakLengthForUser(String userId) {
        UserPomodoroSettings userSettings = getUserSettings(userId);
        return userSettings.getShortBreakLength() > 0 ? userSettings.getShortBreakLength() : getGlobalSettings().getShortBreakLength();
    }






    ////////////////////////////





    public GlobalPomodoroSettings getPomodoroSettings(String userId) {
        // Try to fetch user-specific settings
        UserPomodoroSettings userSettings = userSettingsRepo.findByUserId(userId);

        if (userSettings != null) {
            // If user-specific settings are available, modify and return
            GlobalPomodoroSettings settings = new GlobalPomodoroSettings();
            settings.setPomodoroLength(userSettings.getPomodoroLength() != null ? userSettings.getPomodoroLength() : 25);
            settings.setShortBreakLength(userSettings.getShortBreakLength() != null ? userSettings.getShortBreakLength() : 5);
            settings.setLongBreakLength(userSettings.getLongBreakLength() != null ? userSettings.getLongBreakLength() : 15);
            settings.setLongBreakAfter(userSettings.getLongBreakAfter() != null ? userSettings.getLongBreakAfter() : 4);
            settings.setAutoStartNextPomodoro(userSettings.getAutoStartNextPomodoro() != null ? userSettings.getAutoStartNextPomodoro() : true);
            settings.setAutoStartBreak(userSettings.getAutoStartBreak() != null ? userSettings.getAutoStartBreak() : true);
            settings.setDisableBreak(userSettings.getDisableBreak() != null ? userSettings.getDisableBreak() : false);
            return settings;
        }

        // Fallback to global settings if no user-specific settings exist
        return globalSettingsRepo.findById("global").orElse(new GlobalPomodoroSettings());
    }


}
