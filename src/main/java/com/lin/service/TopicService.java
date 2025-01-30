package com.lin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lin.entity.Flashcard;
import com.lin.entity.Subject;
import com.lin.entity.Topic;
import com.lin.entity.TopicView;
import com.lin.repository.FlashcardRepository;
import com.lin.repository.SubjectRepository;
import com.lin.repository.TopicRepository;
import com.lin.repository.TopicViewRepository;

@Service
public class TopicService {

    private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private TopicViewRepository topicViewRepository;

    public Topic createTopic(String subjectId, String topicName) {
        logger.info("Creating topic with name '{}' for subject ID: {}", topicName, subjectId);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));

        Topic newTopic = new Topic();
        newTopic.setSubject(subject);
        newTopic.setTopicName(topicName);
        newTopic.setId(subjectId + "_" + topicName);

        return topicRepository.save(newTopic);
    }


    public Topic getTopicById(String id) {
        return topicRepository.findById(id).orElse(null); // Using MongoDB's findById method
    }

    
    public Topic createTopicWithDefaultUser(String topicName, String subjectId) {
        logger.info("Creating topic with name '{}' for subject ID '{}' and default user ID 'user1'", topicName, subjectId);
    
        // Fetch the subject to associate with the topic
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));
    
        // Build the new Topic
        Topic newTopic = Topic.builder()
                .id(subjectId + "_" + topicName)
                .userId("user1") // Default user ID
                .topicName(topicName)
                .imageUrl("") // Default or placeholder image URL
                .subject(subject)
                .flashcards(List.of()) // Empty flashcard list
                .build();
    
        // Save the topic to the database
        return topicRepository.save(newTopic);
    }

    
    public Topic updateTopic(String topicId, String subjectId, String topicName) {
        logger.info("Updating topic with ID: {} for subject ID: {}", topicId, subjectId);

        Topic existingTopic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with ID: " + topicId));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));

        existingTopic.setSubject(subject);
        existingTopic.setTopicName(topicName);

        return topicRepository.save(existingTopic);
    }

    public Topic Test(String topicId) {
        System.out.println("Topic ID: " + topicId);
        Optional<Topic> topic = topicRepository.findById(topicId);
        System.out.println("Topic: " + topic);
        return topic.orElse(null);
    }

    public Map<String, Integer> countFlashcardsByDifficulty(String topicId) {
        System.out.println("Topic ID: " + topicId);
        Optional<Topic> topic = topicRepository.findById(topicId);
        System.out.println("Topic: " + topic);
        List<Flashcard> flashcards = flashcardRepository.findByTopic(topic);
        if (flashcards.isEmpty()) {
            System.out.println("t3bt");

        }
        for (Flashcard flashcard : flashcards) {
            int difficulty = flashcard.getDifficulty();
            System.out.println("Difficulty: " + difficulty);
        }
        Map<String, Integer> difficultyCounts = new HashMap<>();
        difficultyCounts.put("easy", 0);
        difficultyCounts.put("medium", 0);
        difficultyCounts.put("hard", 0);

        for (Flashcard flashcard : flashcards) {
            int difficulty = flashcard.getDifficulty();
            System.out.println("Difficulty: " + difficulty);
            switch (difficulty) {
                case 1:
                    difficultyCounts.put("easy", difficultyCounts.get("easy") + 1);
                    break;
                case 2:
                    difficultyCounts.put("medium", difficultyCounts.get("medium") + 1);
                    break;
                case 3:
                    difficultyCounts.put("hard", difficultyCounts.get("hard") + 1);
                    break;
                default:
                    break;
            }
        }

        return difficultyCounts;
    }

    public void deleteTopicById(String topicId) {
        logger.info("Deleting topic with ID: {}", topicId);

        if (!topicRepository.existsById(topicId)) {
            throw new IllegalArgumentException("Topic not found with ID: " + topicId);
        }

        topicRepository.deleteById(topicId);
    }









    public void recordTopicView(String userId, String topicId) {
        TopicView topicView = new TopicView(userId, topicId, LocalDateTime.now());
        topicViewRepository.save(topicView);
    }
    public List<Topic> getRecentTopicsViewedByUser(String userId) {
        List<TopicView> topicViews = topicViewRepository.findTop4ByUserIdOrderByViewedAtDesc(userId);
    
        // Explicitly cast the return type of the lambda to Topic
        return topicViews.stream()
                .map((TopicView topicView) -> topicRepository.findById(topicView.getTopicId()).orElse(null)) // Explicitly typing the lambda
                .filter(topic -> topic != null) // Filter out null topics
                .peek(topic -> {
                    // Print the number of flashcards for each topic
                    int flashcardCount = topic.getFlashcards() != null ? topic.getFlashcards().size() : 0;
                    System.out.println("Topic: " + topic.getTopicName() + " has " + flashcardCount + " flashcards.");
                })
                .collect(Collectors.toList()); // Collect into a list of Topics
    }
}    



