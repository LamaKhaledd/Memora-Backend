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
            subject.setTopics(topics); 
        }

        return subjects;
    }




    public Subject togglePrivacy(String subjectId, String privacy) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();

            // Update privacy based on the input
            subject.setPrivacy(privacy);
            
            // If the privacy is set to protected, ensure sharedUsers list is initialized
            if ("protected".equalsIgnoreCase(privacy) && subject.getSharedUsers() == null) {
                subject.setSharedUsers(new ArrayList<>());
            }

            return subjectRepository.save(subject);
        } else {
            throw new IllegalArgumentException("Subject not found with id: " + subjectId);
        }
    }

    public Subject addUserToProtectedSubject(String subjectId, String userId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();

            if ("protected".equalsIgnoreCase(subject.getPrivacy())) {
                List<String> sharedUsers = subject.getSharedUsers();
                if (!sharedUsers.contains(userId)) {
                    sharedUsers.add(userId);
                    subject.setSharedUsers(sharedUsers);
                    return subjectRepository.save(subject);
                } else {
                    throw new IllegalArgumentException("User already has access to this subject.");
                }
            } else {
                throw new IllegalArgumentException("Subject is not protected.");
            }
        } else {
            throw new IllegalArgumentException("Subject not found with id: " + subjectId);
        }
    }

    public Subject removeUserFromProtectedSubject(String subjectId, String userId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();

            if ("protected".equalsIgnoreCase(subject.getPrivacy())) {
                List<String> sharedUsers = subject.getSharedUsers();
                if (sharedUsers.contains(userId)) {
                    sharedUsers.remove(userId);
                    subject.setSharedUsers(sharedUsers);
                    return subjectRepository.save(subject);
                } else {
                    throw new IllegalArgumentException("User does not have access to this subject.");
                }
            } else {
                throw new IllegalArgumentException("Subject is not protected.");
            }
        } else {
            throw new IllegalArgumentException("Subject not found with id: " + subjectId);
        }
    }
    

    public List<Subject> getSubjectsByUserId(String userId) {
        List<Subject> subjects = subjectRepository.findByUserId(userId); 
        for (Subject subject : subjects) {
            List<Topic> topics = topicRepository.findBySubjectAndUserId(subject, userId);
            for (Topic topic : topics) {
                System.out.println(topic);
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
    
        subjects = subjects.stream()
                .filter(subject -> "public".equalsIgnoreCase(subject.getPrivacy()))
                .toList();
    
        logger.info("Filtered subjects with public privacy: " + subjects.size() + " subjects found.");
    
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

    public Subject toggleFavorite(String subjectId) {
        // Retrieve the subject by ID
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            
            // Toggle the isFavorite property
            subject.setFavorite(!subject.isFavorite());
            
            // Save the updated subject back to the repository
            return subjectRepository.save(subject);
        } else {
            throw new IllegalArgumentException("Subject not found with id: " + subjectId);
        }
    }

    public Optional<Subject> getSubjectByName(String subjectName) {
        logger.info("Fetching subject by name: " + subjectName);
        Optional<Subject> subject = subjectRepository.findBySubjectNameIgnoreCase(subjectName);
    
        if (!subject.isPresent()) {
            logger.warn("Subject with name " + subjectName + " not found.");
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
