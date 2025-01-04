package com.lin.controller;
import com.lin.entity.Flashcard;
import com.lin.entity.StudySession;
import com.lin.service.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/study-sessions") // Updated endpoint for clarity
public class StudySessionController {

    @Autowired
    private StudySessionService studySessionService;

    // DTO to encapsulate the questions
    public static class StudySessionRequest {
        private String subjectId;
        private String topicName;
        private String topicId;
        private List<Flashcard> easyQuestions;
        private List<Flashcard> mediumQuestions;
        private List<Flashcard> hardQuestions;

        // Getters and setters
        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public List<Flashcard> getEasyQuestions() {
            return easyQuestions;
        }

        public void setEasyQuestions(List<Flashcard> easyQuestions) {
            this.easyQuestions = easyQuestions;
        }

        public List<Flashcard> getMediumQuestions() {
            return mediumQuestions;
        }

        public void setMediumQuestions(List<Flashcard> mediumQuestions) {
            this.mediumQuestions = mediumQuestions;
        }

        public List<Flashcard> getHardQuestions() {
            return hardQuestions;
        }

        public void setHardQuestions(List<Flashcard> hardQuestions) {
            this.hardQuestions = hardQuestions;
        }
    }

    @PostMapping("/create")
    public StudySession createStudySession(@RequestBody StudySessionRequest request) {
        return studySessionService.createStudySession(
                request.getSubjectId(),
                request.getTopicName(),
                request.getTopicId(),
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
