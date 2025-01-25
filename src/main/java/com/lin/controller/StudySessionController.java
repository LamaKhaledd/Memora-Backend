package com.lin.controller;
import com.lin.entity.Flashcard;
import com.lin.entity.StudySession;
import com.lin.service.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/study-sessions") // Updated endpoint for clarity
public class StudySessionController {

    @Autowired
    StudySessionService studySessionService;

    @PostMapping("/create")
    public StudySession createStudySession(@RequestBody StudySession request) {
        return studySessionService.createStudySession(
                request.getSubjectId(),
                request.getTopicName(),
                request.getTopicId(),
                request.getCorrectAnswerCount(),
                request.getIncorrectAnswerCount(),
                request.getTotalQuestions(),
                request.getTotalTimeSpent(),
                request.getEasyQuestionCount(),
                request.getMediumQuestionCount(),
                request.getHardQuestionCount(),
                request.getEasyQuestions(),
                request.getMediumQuestions(),
                request.getHardQuestions()
        );
    }
    
    @GetMapping("/all")
    public List<StudySession> getAllStudySessions() {
        return studySessionService.getAllStudySessions();
    }
}
