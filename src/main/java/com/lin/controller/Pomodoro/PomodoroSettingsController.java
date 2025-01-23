package com.lin.controller.Pomodoro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lin.entity.Pomodoro.GlobalPomodoroSettings;
import com.lin.entity.Pomodoro.UserPomodoroSettings;
import com.lin.repository.Pomodoro.UserPomodoroSettingsRepository;
import com.lin.service.Pomodoro.PomodoroSettingsService;
import com.lin.service.Pomodoro.UserPomodoroSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class PomodoroSettingsController {

    @Autowired
    private PomodoroSettingsService settingsService;
    @Autowired
    private UserPomodoroSettingsService userSettingsService;

    @Autowired
    UserPomodoroSettingsRepository userPomodoroSettingsRepository;


    @GetMapping("/global")
    public ResponseEntity<GlobalPomodoroSettings> getGlobalSettings() {
        try {
            GlobalPomodoroSettings globalSettings = settingsService.getGlobalSettings();
            return ResponseEntity.ok(globalSettings);
        } catch (Exception e) {
            // Handle errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/global")
    public void updateGlobalSettings(@RequestBody GlobalPomodoroSettings settings) {
        settingsService.updateGlobalSettings(settings);
    }
/*
    @GetMapping("/user/{userId}")
    public UserPomodoroSettings getUserSettings(@PathVariable String userId) {
        return settingsService.getUserSettings(userId);
    }*/







    private static final Logger logger = LoggerFactory.getLogger(PomodoroSettingsController.class);

    @PutMapping("/user/{userId}")
    public void updateUserSettings(@PathVariable String userId, @RequestBody UserPomodoroSettings settings) {
        // Log the received data
        logger.info("Received request to update settings for user ID: {}", userId);
        logger.info("Received settings: {}", settings);

        // Ensure the userId is set in the settings object
        settings.setUserId(userId);

        // Log the settings after setting the userId
        logger.info("Updated settings with user ID: {}", settings);

        // Call the service to update or create settings
        try {
            settingsService.updateUserSettings(settings); // Update or create settings
            logger.info("User settings updated or created successfully for user ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error updating user settings for user ID: {}", userId, e);
            throw e; // Optional: rethrow the exception if you want to propagate it
        }
    }


    @GetMapping("/{userId}")
    public Map<String, Object> getUserSettings(@PathVariable String userId) {
        return userSettingsService.getEffectiveSettings(userId);
    }

    /////////////////////////////////////////
    @GetMapping("/pomodoro/{userId}")
    public GlobalPomodoroSettings getPomodoroSettings(@PathVariable String userId) {
        // Get settings for the user, or global settings if no user-specific settings are available
        return settingsService.getPomodoroSettings(userId);
    }


}
