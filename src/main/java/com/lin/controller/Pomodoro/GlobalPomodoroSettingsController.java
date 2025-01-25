package com.lin.controller.Pomodoro;

import com.lin.entity.Pomodoro.GlobalPomodoroSettings;
import com.lin.service.Pomodoro.GlobalPomodoroSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/global-settings")
public class GlobalPomodoroSettingsController {

    @Autowired
    private GlobalPomodoroSettingsService globalSettingsService;

    @GetMapping
    public GlobalPomodoroSettings getGlobalSettings() {
        return globalSettingsService.getGlobalSettings();
    }

    @PutMapping
    public GlobalPomodoroSettings updateGlobalSettings(@RequestBody GlobalPomodoroSettings updatedSettings) {
        return globalSettingsService.updateGlobalSettings(updatedSettings);
    }
}
