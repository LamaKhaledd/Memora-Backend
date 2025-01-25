package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.GlobalPomodoroSettings;
import com.lin.entity.Pomodoro.UserPomodoroSettings;
import com.lin.repository.Pomodoro.UserPomodoroSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserPomodoroSettingsService {
    @Autowired
    private UserPomodoroSettingsRepository userSettingsRepository;

    @Autowired
    private GlobalPomodoroSettingsService globalSettingsService;

    public UserPomodoroSettings getUserSettings(String userId) {
        return userSettingsRepository.findById(userId)
                .orElse(null); // Return null if no custom settings exist
    }

    public UserPomodoroSettings updateUserSettings(String userId, UserPomodoroSettings newSettings) {
        newSettings.setUserId(userId);
        return userSettingsRepository.save(newSettings);
    }



    public Map<String, Object> getEffectiveSettings(String userId) {
        GlobalPomodoroSettings globalSettings = globalSettingsService.getGlobalSettings();
        UserPomodoroSettings userSettings = getUserSettings(userId);

        Map<String, Object> effectiveSettings = new HashMap<>();
        effectiveSettings.put("pomodoroLength", userSettings != null && userSettings.getPomodoroLength() != null
                ? userSettings.getPomodoroLength()
                : globalSettings.getPomodoroLength());
        effectiveSettings.put("shortBreakLength", userSettings != null && userSettings.getShortBreakLength() != null
                ? userSettings.getShortBreakLength()
                : globalSettings.getShortBreakLength());
        effectiveSettings.put("longBreakLength", userSettings != null && userSettings.getLongBreakLength() != null
                ? userSettings.getLongBreakLength()
                : globalSettings.getLongBreakLength());
        effectiveSettings.put("longBreakAfter", userSettings != null && userSettings.getLongBreakAfter() != null
                ? userSettings.getLongBreakAfter()
                : globalSettings.getLongBreakAfter());
        effectiveSettings.put("autoStartNextPomodoro", userSettings != null && userSettings.getAutoStartNextPomodoro() != null
                ? userSettings.getAutoStartNextPomodoro()
                : globalSettings.isAutoStartNextPomodoro());
        effectiveSettings.put("autoStartBreak", userSettings != null && userSettings.getAutoStartBreak() != null
                ? userSettings.getAutoStartBreak()
                : globalSettings.isAutoStartBreak());
        effectiveSettings.put("disableBreak", userSettings != null && userSettings.getDisableBreak() != null
                ? userSettings.getDisableBreak()
                : globalSettings.isDisableBreak());

        return effectiveSettings;
    }










}
