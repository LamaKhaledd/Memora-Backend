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
            int i, int j, int k, String total, int l, int m, int n, List<Flashcard> easyQuestions,
            List<Flashcard> mediumQuestions, List<Flashcard> hardQuestions) {

        StudySession studySession = new StudySession();
        studySession.setSubjectId(subjectId);
        studySession.setTopicName(topicName);
        studySession.setTopicId(topicId);
        studySession.setEasyQuestions(easyQuestions);
        studySession.setMediumQuestions(mediumQuestions);
        studySession.setHardQuestions(hardQuestions);
        studySession.setCorrectAnswerCount(i);
        studySession.setIncorrectAnswerCount(j);
        studySession.setTotalQuestions(easyQuestions.size() + mediumQuestions.size() + hardQuestions.size());

        studySession.setEasyQuestionCount(easyQuestions.size());
        studySession.setMediumQuestionCount(mediumQuestions.size());
        studySession.setHardQuestionCount(hardQuestions.size());

        studySession.setTotalTimeSpent(total);

        studySession.setCreatedAt(LocalDateTime.now());

        return studySessionRepository.save(studySession);
    }

    public List<StudySession> getAllStudySessions() {
        return studySessionRepository.findAll();
    }
}
