package com.lin.controller.Pomodoro;

import com.lin.entity.Pomodoro.Timer;
import com.lin.service.Pomodoro.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/timers")
public class TimerController {
    @Autowired
    private TimerService timerService;

    // Start a new Pomodoro timer
    @PostMapping("/start")
    public ResponseEntity<String> startPomodoro(
            @RequestParam String userId,
            @RequestParam String taskId,
            @RequestParam long pomodoroDuration,
            @RequestParam long breakDuration) {
        try {
            timerService.startPomodoro(userId, taskId, pomodoroDuration, breakDuration);
            return ResponseEntity.ok("Pomodoro timer started successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Stop the active Pomodoro timer
    @PostMapping("/stop")
    public ResponseEntity<String> stopPomodoro(@RequestParam String userId) {
        try {
            timerService.stopPomodoro(userId);
            return ResponseEntity.ok("Pomodoro timer stopped successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Pause the active Pomodoro timer
    @PostMapping("/pause")
    public ResponseEntity<String> pausePomodoro(@RequestParam String userId) {
        try {
            timerService.pausePomodoro(userId);
            return ResponseEntity.ok("Pomodoro timer paused successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Resume the paused Pomodoro timer
    @PostMapping("/resume")
    public ResponseEntity<String> resumePomodoro(@RequestParam String userId) {
        try {
            timerService.resumePomodoro(userId);
            return ResponseEntity.ok("Pomodoro timer resumed successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Switch to a new task
    @PostMapping("/switch-task")
    public ResponseEntity<String> switchTask(
            @RequestParam String userId,
            @RequestParam String currentTaskId,
            @RequestParam String newTaskId) {
        try {
            timerService.switchTask(userId, currentTaskId, newTaskId);
            return ResponseEntity.ok("Task switched successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Skip the break and continue Pomodoro
    @PostMapping("/skip-break")
    public ResponseEntity<String> skipBreak(@RequestParam String userId) {
        try {
            timerService.skipBreak(userId);
            return ResponseEntity.ok("Break skipped. Continuing Pomodoro.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // End the break and continue Pomodoro
    @PostMapping("/end-break")
    public ResponseEntity<String> endBreak(@RequestParam String userId) {
        try {
            timerService.endBreak(userId);
            return ResponseEntity.ok("Break ended. Continuing Pomodoro.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Reset all timers on app restart
    @PostMapping("/reset")
    public ResponseEntity<String> resetTimers() {
        try {
            timerService.resetTimersOnRestart();
            return ResponseEntity.ok("All timers reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Fetch the current state of the timer
    @GetMapping("/state")
    public ResponseEntity<Timer> getTimerState(@RequestParam String userId) {
        try {
            Timer timer = timerService.getTimerState(userId);
            if (timer != null) {
                return ResponseEntity.ok(timer);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}
