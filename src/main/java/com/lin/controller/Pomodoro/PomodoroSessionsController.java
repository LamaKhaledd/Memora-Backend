package com.lin.controller.Pomodoro;

import com.lin.entity.Pomodoro.PomodoroSessions;
import com.lin.entity.Pomodoro.Timer;
import com.lin.repository.Pomodoro.PomodoroSessionsRepository;
import com.lin.service.Pomodoro.PomodoroSessionsService;
import com.lin.service.Pomodoro.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/sessions")
public class PomodoroSessionsController {

    @Autowired
    private PomodoroSessionsService sessionService;

    @Autowired
    private PomodoroSessionsRepository sessionRepository;

    @Autowired
    private TimerService timerService;

    // Start a Pomodoro session and the associated timer
    @PostMapping("/start")
    public PomodoroSessions startSession(@RequestParam String userId, @RequestParam String taskId) {

        return sessionService.startSession(userId, taskId);
    }
    @PostMapping("/startPomodoro/{userId}")
    public ResponseEntity<Map<String, Object>> startPomodoroTimer(@PathVariable String userId) {
        timerService.startPomodoro(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Pomodoro timer started successfully");
        return ResponseEntity.ok(response);
    }



    // Start a break and the associated timer
    @PostMapping("/startBreak")
    public void startBreak(@RequestParam String userId,@RequestParam int remainingTime) {
        timerService.startBreak(userId,remainingTime);
    }


    // Update study time for a session
    @PutMapping("/update/{sessionId}")
    public void updateSessionStudyTime(@PathVariable String sessionId, @RequestParam long studyTime) {
        sessionService.updateSessionStudyTime(sessionId, studyTime);
    }









    // Complete a Pomodoro session
    @PutMapping("/complete/{sessionId}")
    public void completeSession(@PathVariable String sessionId) {
        PomodoroSessions session = sessionService.findSessionById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found: " + sessionId));

        // Mark session as completed
        session.setCompleted(true);
        sessionRepository.save(session);

        // Stop the Pomodoro timer for the user
        timerService.pauseTimer(session.getUserId());
    }

    // Get session details by session ID
    @GetMapping("/{sessionId}")
    public Optional<PomodoroSessions> getSession(@PathVariable String sessionId) {
        return sessionService.findSessionById(sessionId);
    }
    // Get session details by session ID
    @GetMapping("user/{userId}")
    public Timer getTimer(@PathVariable String userId) {
        return timerService.getTimer(userId);
    }
    // Pause the Pomodoro timer
    @PutMapping("/pause/timer")
    public ResponseEntity<Timer> pauseTimer(
            @RequestParam String userId,
            @RequestParam int remainingTime) {
        try {
            // Get the timer for the user
            Timer timer = timerService.getTimer(userId);

            if (timer != null ) {
                // Pause the timer and update remaining time
                timer.setActive(false);
                timer.setPaused(true);
                timer.setRemainingTime(remainingTime);
                timerService.updateTimer(timer);  // Update timer in service

                return ResponseEntity.ok(timer);  // Return the updated timer
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);  // No active timer found for the user
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Resume the Pomodoro timer
    @PutMapping("/resume")
    public ResponseEntity<?> resumeTimer(@RequestParam String userId) {
        try {
            // Retrieve the user's timer state
            Timer timer = timerService.getTimer(userId);
            if (timer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Timer not found for user ID: " + userId);
            }

            // Ensure the timer was paused
            if (!timer.isPaused()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Timer is not paused.");
            }

            // Resume the timer
            timer.setPaused(false);
            timer.setActive(true);
            timerService.updateTimer(timer);

            // Return the remaining time to the front end
            return ResponseEntity.ok(Map.of(
                    "remainingTime", timer.getRemainingTime()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resuming timer: " + e.getMessage());
        }
    }

    // Resume the Pomodoro timer
    @PutMapping("/mode")
    public ResponseEntity<?> updateTimerMode(@RequestParam String userId,@RequestParam String mode) {
        try {
            // Retrieve the user's timer state
            Timer timer = timerService.getTimer(userId);
            if (timer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Timer not found for user ID: " + userId);
            }

            timer.setTimerMode(mode);

            // Return the remaining time to the front end
            return ResponseEntity.ok(Map.of(
                    "remainingTime", timer.getTimerMode()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating timer mode: " + e.getMessage());
        }
    }

}
