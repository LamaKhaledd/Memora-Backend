package com.lin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lin.entity.Flashcard;
import com.lin.entity.Subject;
import com.lin.entity.Topic;
import com.lin.entity.User;
import com.lin.entity.UserFlashcardRelationship;
import com.lin.repository.FlashcardRepository;
import com.lin.repository.SubjectRepository;
import com.lin.repository.TopicRepository;
import com.lin.repository.UserFlashcardRelationshipRepository;
import com.lin.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlashcardRepository flashcardRepository; 

    @Autowired
    private UserFlashcardRelationshipRepository userFlashcardRelationshipRepository;


    public List<Subject> getSubjectsWithTopicsAndFlashcardsByUserId(String userId) {
        System.out.println(userId);
        List<Subject> subjects = subjectRepository.findByUserId(userId); 
        for (Subject subject : subjects) {
            List<Topic> topics = topicRepository.findBySubjectAndUserId(subject, userId);
            for (Topic topic : topics) {
                List<Flashcard> flashcards = flashcardRepository.findByTopic(topic);
                topic.setFlashcards(flashcards);
            }
            subject.setTopics(topics); // Set filtered topics to the subject
        }

        return subjects;
    }

    public List<Subject> getAllSubjectsWithTopicsAndFlashcards() {
        List<Subject> subjects = subjectRepository.findAll();
        logger.info("Fetched all subjects: " + subjects.size() + " subjects found.");

        for (Subject subject : subjects) {
            List<Topic> topics = topicRepository.findBySubject(subject);
            for (Topic topic : topics) {
                List<Flashcard> flashcards = flashcardRepository.findByTopic(topic);
                topic.setFlashcards(flashcards);
            }
            subject.setTopics(topics); 
        }
        return subjects;
    }

    public Optional<Subject> getSubjectById(String id) {
        logger.info("Fetching subject by ID: " + id);
        Optional<Subject> subject = subjectRepository.findById(id);

        if (!subject.isPresent()) {
            logger.warn("Subject with ID " + id + " not found.");
        }

        return subject;
    }

    public Subject saveSubject(Subject subject) {
        logger.info("Saving subject: " + subject.getSubjectName());
        return subjectRepository.save(subject);
    }

    public void deleteSubject(String id) {
        logger.info("Deleting subject with ID: " + id);
        subjectRepository.deleteById(id);
    }
}
