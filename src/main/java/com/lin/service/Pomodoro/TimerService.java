package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.*;
import com.lin.repository.Pomodoro.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TimerService {

    @Autowired
    private TimerRepository timerRepository;

    @Autowired
    private UserPomodoroSettingsRepository userPomodoroSettingsRepository;

    @Autowired
    private GlobalPomodoroSettingsRepository globalPomodoroSettingsRepository;

    @Autowired
    private PomodoroSessionsRepository pomodoroSessionsRepository;


    // Pause the Pomodoro timer for the user
    public void pauseTimer(String userId) {
        Timer timer = timerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Timer not found for userId: " + userId));

        if (timer.isActive()) {
            // Calculate the remaining time and stop the timer
            long elapsedTime = Instant.now().getEpochSecond() - timer.getLastSwitchTime().getEpochSecond();
            timer.setRemainingTime(Math.max(0, timer.getRemainingTime() - elapsedTime));
        }

        // Mark the timer as inactive (paused)
        timer.setActive(false);
        timer.setLastSwitchTime(Instant.now()); // Update the last switch time
        timerRepository.save(timer);

    }

    // Resume the Pomodoro timer for the user
    public Timer resumeTimer(String userId) {
        Timer timer = timerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Timer not found for userId: " + userId));

        // Resume the timer
        timer.setActive(true);
        timerRepository.save(timer);

        return timer;
    }



    // Start the break timer
    public void startBreak(String userId, int remainingTime) {

        // Fetch or create the timer for the user
        Timer timer = timerRepository.findByUserId(userId)
                .orElseGet(() -> new Timer(userId));

        // Set remaining time and activate timer
        timer.setRemainingTime(remainingTime);
        timer.setActive(true);
        timer.setBreakTimerFinished(false);

        // Save updated timer
        timerRepository.save(timer);

    }
    public void startPomodoro(String userId) {
        // Fetch user-specific settings
        Optional<UserPomodoroSettings> userSettingsOpt = userPomodoroSettingsRepository.findById(userId);

        if (userSettingsOpt.isPresent()) {
            // If user settings exist, use them
            UserPomodoroSettings userSettings = userSettingsOpt.get();

            Timer timer = timerRepository.findByUserId(userId)
                    .orElseGet(() -> new Timer(userId));

            timer.setRemainingTime(userSettings.getPomodoroLength() * 60);  // Convert minutes to seconds
            timer.setActive(true);
            timer.setPomodoroTimerFinished(false);
            timerRepository.save(timer);

        } else {
            // If no user settings, fall back to global settings
            GlobalPomodoroSettings globalSettings = globalPomodoroSettingsRepository.findById("global")
                    .orElseThrow(() -> new RuntimeException("Global settings not found"));

            Timer timer = timerRepository.findByUserId(userId)
                    .orElseGet(() -> new Timer(userId));

            timer.setRemainingTime(globalSettings.getPomodoroLength() * 60L);  // Convert minutes to seconds
            timer.setActive(true);
            timer.setPomodoroTimerFinished(false);
            timerRepository.save(timer);
        }
    }



    public void updateTimer(Timer timer) {
        // Update the timer object in the database
        timerRepository.save(timer);
    }








    // Fetch Timer for a user
    public Timer getTimer(String userId) {
        return timerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Timer not found for userId: " + userId));
    }
    private long getPomodoroDurationForUser(String userId) {
        UserPomodoroSettings userSettings = userPomodoroSettingsRepository.findByUserId(userId);
        if (userSettings != null) {
            return userSettings.getPomodoroLength();
        }
        GlobalPomodoroSettings globalSettings = globalPomodoroSettingsRepository.findById("global").orElse(null);
        return globalSettings != null ? globalSettings.getPomodoroLength() : 25 * 60;
    }

    private long getBreakDurationForUser(String userId) {
        UserPomodoroSettings userSettings = userPomodoroSettingsRepository.findByUserId(userId);
        if (userSettings != null) {
            return userSettings.getShortBreakLength() != null ? userSettings.getShortBreakLength() * 60L : 5 * 60L;
        }
        GlobalPomodoroSettings globalSettings = globalPomodoroSettingsRepository.findById("global").orElse(null);
        return globalSettings != null ? globalSettings.getShortBreakLength() * 60L : 5 * 60L;
    }



    public Timer updateTimerState(String userId) {
        Timer timer = timerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Timer not found"));

        long elapsedTime = Instant.now().getEpochSecond() - timer.getLastSwitchTime().getEpochSecond();
        long newRemainingTime = Math.max(0, timer.getRemainingTime() - elapsedTime);

        timer.setRemainingTime(newRemainingTime);
        timer.setLastSwitchTime(Instant.now());

        if (newRemainingTime == 0) {
            timer.setActive(false);
            if (timer.isBreakTimerFinished()) {
                timer.setPomodoroTimerFinished(true);
            } else {
                timer.setBreakTimerFinished(true);
            }
        }

        return timerRepository.save(timer);
    }



}
