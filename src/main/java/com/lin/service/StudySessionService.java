package com.lin.service;

import com.lin.entity.Flashcard;
import com.lin.entity.StudySession;
import com.lin.repository.StudySessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudySessionService {

    @Autowired
    private StudySessionRepository studySessionRepository;

    public StudySession createStudySession(String subjectId, String topicName, String topicId,
            List<Flashcard> easyQuestions,
            List<Flashcard> mediumQuestions, List<Flashcard> hardQuestions) {

        StudySession studySession = new StudySession();
        studySession.setSubjectId(subjectId);
        studySession.setTopicName(topicName);
        studySession.setTopicId(topicId);
        studySession.setEasyQuestions(easyQuestions);
        studySession.setMediumQuestions(mediumQuestions);
        studySession.setHardQuestions(hardQuestions);

        studySession.setCorrectAnswerCount(0);
        studySession.setIncorrectAnswerCount(0);
        studySession.setTotalQuestions(easyQuestions.size() + mediumQuestions.size() + hardQuestions.size());

        studySession.setEasyQuestionCount(easyQuestions.size());
        studySession.setMediumQuestionCount(mediumQuestions.size());
        studySession.setHardQuestionCount(hardQuestions.size());

        studySession.setTotalTimeSpent("00:00");

        studySession.setCreatedAt(LocalDateTime.now());

        return studySessionRepository.save(studySession);
    }

    public List<StudySession> getAllStudySessions() {
        return studySessionRepository.findAll();
    }
}
