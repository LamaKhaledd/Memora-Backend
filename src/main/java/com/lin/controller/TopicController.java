package com.lin.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lin.DTO.TopicRequest;
import com.lin.entity.Topic;
import com.lin.service.TopicService;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody TopicRequest topicRequest) {
        System.out.println("Received TopicRequest:");
        System.out.println("Subject ID: " + topicRequest.getSubjectId());
        System.out.println("Topic Name: " + topicRequest.getTopicName());
        Topic createdTopic = topicService.createTopic(topicRequest.getSubjectId(), topicRequest.getTopicName());
        return ResponseEntity.status(201).body(createdTopic);
    }


    
    @GetMapping("/{id}/flashcard-counts")
    public ResponseEntity<Map<String, Integer>> getFlashcardCountsByDifficulty(@PathVariable String id) {
        System.out.println(id);
        Map<String, Integer> difficultyCounts = topicService.countFlashcardsByDifficulty(id);
        return ResponseEntity.ok(difficultyCounts);
    }



    
    // Update a topic
    @PutMapping
    public ResponseEntity<Topic> updateTopic(@RequestBody TopicRequest topicRequest) {
        Topic updatedTopic = topicService.updateTopic(topicRequest.getId(), topicRequest.getSubjectId(), topicRequest.getTopicName());
        return ResponseEntity.ok(updatedTopic);
    }

    // Delete a topic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String id) { // Change to String for MongoDB ObjectId
        topicService.deleteTopicById(id); // Ensure service method accepts String id
        return ResponseEntity.noContent().build();
    }
}
