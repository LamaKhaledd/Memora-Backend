package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.Timer;
import com.lin.repository.Pomodoro.TimerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TimerService {

    @Autowired
    private TimerRepository timerRepository;


    public TimerService(TimerRepository timerRepository) {
        this.timerRepository = timerRepository;
    }


    // Method to start a new Pomodoro timer
    public void startPomodoro(String userId, String taskId, long pomodoroDuration, long breakDuration) {
        // Ensure no active timer already exists for the user
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isPresent()) {
            throw new IllegalStateException("A Pomodoro session is already active for this user.");
        }

        Timer newTimer = new Timer(userId, taskId, pomodoroDuration, breakDuration);
        timerRepository.save(newTimer);
    }

    // Method to stop the active Pomodoro timer
    public void stopPomodoro(String userId) {
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isPresent()) {
            Timer timer = activeTimer.get();
            long elapsedTime = calculateElapsedTime(timer.getLastSwitchTime(), Instant.now());

            if (!timer.isBreakTime()) {
                timer.addTaskTime(timer.getTaskId(), elapsedTime);
            }

            timer.setActive(false); // Mark timer as inactive
            timerRepository.save(timer);
        } else {
            throw new IllegalStateException("No active Pomodoro session found for this user.");
        }
    }

    // Method to pause the active Pomodoro timer
    public void pausePomodoro(String userId) {
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isPresent()) {
            Timer timer = activeTimer.get();
            long elapsedTime = calculateElapsedTime(timer.getLastSwitchTime(), Instant.now());

            if (!timer.isBreakTime()) {
                timer.addTaskTime(timer.getTaskId(), elapsedTime);
            }

            timer.setActive(false);
            timer.setRemainingTime(timer.getRemainingTime() - elapsedTime);
            timerRepository.save(timer);
        } else {
            throw new IllegalStateException("No active Pomodoro session found for this user.");
        }
    }

    // Method to resume the paused Pomodoro timer
    public void resumePomodoro(String userId) {
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isEmpty()) {
            throw new IllegalStateException("No paused Pomodoro session found for this user.");
        }

        Timer timer = activeTimer.get();
        timer.setActive(true);
        timer.setLastSwitchTime(Instant.now());
        timerRepository.save(timer);
    }

    // Method to switch between tasks
    public void switchTask(String userId, String currentTaskId, String newTaskId) {
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isPresent()) {
            Timer timer = activeTimer.get();
            if (timer.isBreakTime()) {
                throw new IllegalStateException("Cannot switch tasks during break time.");
            }

            long elapsedTime = calculateElapsedTime(timer.getLastSwitchTime(), Instant.now());
            timer.addTaskTime(currentTaskId, elapsedTime);

            timer.setTaskId(newTaskId);
            timer.setLastSwitchTime(Instant.now());
            timer.setLastKnownState("Switched to Task: " + newTaskId); // Update state after switching tasks
            timerRepository.save(timer);
        } else {
            throw new IllegalStateException("No active Pomodoro session found for this user.");
        }
    }

    // Method to skip the break and continue the Pomodoro session
    public void skipBreak(String userId) {
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isPresent()) {
            Timer timer = activeTimer.get();
            if (!timer.isBreakTime()) {
                throw new IllegalStateException("Cannot skip break time outside of a break.");
            }

            timer.setBreakTime(false);
            timer.setRemainingTime(timer.getRemainingTime());
            timer.setLastSwitchTime(Instant.now());
            timerRepository.save(timer);
        } else {
            throw new IllegalStateException("No active Pomodoro session found for this user.");
        }
    }

    // Method to end the break
    public void endBreak(String userId) {
        Optional<Timer> activeTimer = timerRepository.findActiveTimerByUserId(userId);
        if (activeTimer.isPresent()) {
            Timer timer = activeTimer.get();
            if (!timer.isBreakTime()) {
                throw new IllegalStateException("Cannot end break time outside of a break.");
            }

            timer.setBreakTime(false);
            timer.setRemainingTime(timer.getRemainingTime());
            timer.setLastSwitchTime(Instant.now());
            timerRepository.save(timer);
        } else {
            throw new IllegalStateException("No active Pomodoro session found for this user.");
        }
    }

    private long calculateElapsedTime(Instant start, Instant end) {
        return Duration.between(start, end).getSeconds();
    }

    // Periodic task to auto-save timer states
    @Scheduled(fixedRate = 5000) // Every 5 seconds
    public void autoSaveTimers() {
        List<Timer> activeTimers = timerRepository.findByIsActive(true);
        for (Timer timer : activeTimers) {
            if (timerHasChanged(timer)) {
                timer.setLastUpdatedTime(Instant.now());
                timerRepository.save(timer);
            }
        }
    }

    // Helper method to check if the timer has changed
    private boolean timerHasChanged(Timer timer) {
        Optional<Timer> dbTimerOpt = timerRepository.findById(timer.getId());
        if (!dbTimerOpt.isPresent()) {
            return true; // Timer is new, should save
        }
        Timer dbTimer = dbTimerOpt.get();
        return dbTimer.getRemainingTime() != timer.getRemainingTime() ||
                dbTimer.isActive() != timer.isActive() ||
                !dbTimer.getTaskId().equals(timer.getTaskId());
    }

    // Reset all timers on restart
    public void resetTimersOnRestart() {
        List<Timer> allTimers = timerRepository.findAll();
        for (Timer timer : allTimers) {
            if (timer.isActive()) {
                timer.setActive(false);
                timer.setLastKnownState("Stopped");
                timerRepository.save(timer);
            }
        }
    }
    public Timer getTimerState(String userId) {
        // Fetch the user's active timer. Assuming a user can have only one active timer at a time.
        Timer timer = timerRepository.findByUserIdAndActive(userId, true);
        return timer;
    }

}
