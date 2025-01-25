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
    public ResponseEntity<Topic> createTopic(@RequestParam String subjectId, @RequestParam String topicName) {
        System.out.println("Received TopicRequest:");
        System.out.println("Subject ID: " + subjectId);
        System.out.println("Topic Name: " + topicName);
        Topic createdTopic = topicService.createTopic(subjectId, topicName);
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


    @PostMapping("/create-with-default-user")
    public ResponseEntity<Topic> createTopicWithDefaultUser(
            @RequestParam String topicName,
            @RequestParam String subjectId) {
        Topic createdTopic = topicService.createTopicWithDefaultUser(topicName, subjectId);
        return ResponseEntity.status(201).body(createdTopic);
    }


    // Delete a topic
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String id) { // Change to String for MongoDB ObjectId
        topicService.deleteTopicById(id); // Ensure service method accepts String id
        return ResponseEntity.noContent().build();
    }
}
