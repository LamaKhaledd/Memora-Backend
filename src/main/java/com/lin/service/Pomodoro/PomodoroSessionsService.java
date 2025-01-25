package com.lin.service.Pomodoro;

import com.lin.entity.Pomodoro.*;
import com.lin.repository.Pomodoro.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PomodoroSessionsService {

    @Autowired
    private PomodoroSessionsRepository sessionRepository;

    @Autowired
    private TimerService timerService; // Only for Timer operations

    public PomodoroSessions startSession(String userId, String taskId) {
        // Create and save session
        PomodoroSessions session = new PomodoroSessions(userId, taskId);
        session.setSessionId(UUID.randomUUID().toString());
        session.setStartTime(Instant.now());
        sessionRepository.save(session);

        return session;
    }

    public Optional<PomodoroSessions> findSessionById(String sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public void updateSessionStudyTime(String sessionId, long studyTime) {
        Optional<PomodoroSessions> sessionOpt = sessionRepository.findById(sessionId);
        if (sessionOpt.isPresent()) {
            PomodoroSessions session = sessionOpt.get();
            //System.out.println("Previous Total Study Time: " + session.getTotalStudyTimeInSeconds());
            //System.out.println("Adding Study Time: " + studyTime);
            session.setTotalStudyTimeInSeconds(studyTime);
            sessionRepository.save(session);
            System.out.println("Updated Total Study Time: " + session.getTotalStudyTimeInSeconds());
        } else {
            throw new RuntimeException("Session not found: " + sessionId);
        }
    }


    public void completeSession(String sessionId) {
        PomodoroSessions session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setEndTime(Instant.now());
        session.setCompleted(true);
        sessionRepository.save(session);
    }
}
