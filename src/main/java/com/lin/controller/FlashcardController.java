package com.lin.controller;
import com.lin.DTO.FlashcardRequest;
import com.lin.entity.Flashcard;
import com.lin.entity.Subject;
import com.lin.entity.Topic;
import com.lin.repository.SubjectRepository;
import com.lin.repository.TopicRepository;
import com.lin.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired        
    private TopicRepository topicRepository;

    // Get all flashcards
    @GetMapping
    public ResponseEntity<List<Flashcard>> getAllFlashcards() {
        List<Flashcard> flashcards = flashcardService.getAllFlashcards();
        return ResponseEntity.ok(flashcards);
    }



    // Update flashcard after review
    @PostMapping("/review/{id}")
    public ResponseEntity<Flashcard> reviewFlashcard(@PathVariable String id, @RequestParam int responseQuality) {
        Flashcard updatedFlashcard = flashcardService.updateFlashcardAfterReview(id, responseQuality);
        return ResponseEntity.ok(updatedFlashcard);
    }
    
    // Get flashcard by ID
    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcardById(@PathVariable String id) {
        Optional<Flashcard> flashcard = flashcardService.getFlashcardById(id);
        if (flashcard.isPresent()) {
            return ResponseEntity.ok(flashcard.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new flashcard
    
        @PostMapping
    public ResponseEntity<Flashcard> createFlashcard(@RequestBody FlashcardRequest flashcardRequest) {
        // Create the Flashcard object from FlashcardRequest
        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion(flashcardRequest.getQuestion());
        flashcard.setAnswer(flashcardRequest.getAnswer());
        flashcard.setDifficulty(flashcardRequest.getDifficulty());

        // Fetch Subject and Topic based on their IDs
        Subject subject = subjectRepository.findById(flashcardRequest.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + flashcardRequest.getSubjectId()));
        Topic topic = topicRepository.findById(flashcardRequest.getTopicId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid topicId: " + flashcardRequest.getTopicId()));

        // Set the subject and topic for the flashcard
        flashcard.setSubject(subject);
        flashcard.setTopic(topic);

        // Save the Flashcard
        Flashcard savedFlashcard = flashcardService.saveFlashcard(flashcard);
        
        return ResponseEntity.status(201).body(savedFlashcard);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Flashcard> updateFlashcard(@PathVariable String id, @RequestBody Flashcard updatedFlashcard) {
        Optional<Flashcard> existingFlashcard = flashcardService.getFlashcardById(id);
        if (existingFlashcard.isPresent()) {
            Flashcard flashcard = existingFlashcard.get();
            flashcard.setQuestion(updatedFlashcard.getQuestion());
            flashcard.setAnswer(updatedFlashcard.getAnswer());
            flashcard.setDifficulty(updatedFlashcard.getDifficulty());
            flashcard.setImageUrl(updatedFlashcard.getImageUrl());

            Flashcard savedFlashcard = flashcardService.saveFlashcard(flashcard);
            return ResponseEntity.ok(savedFlashcard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a flashcard
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable String id) {
        Optional<Flashcard> existingFlashcard = flashcardService.getFlashcardById(id);
        if (existingFlashcard.isPresent()) {
            flashcardService.deleteFlashcard(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get flashcards by topic ID
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByTopicId(@PathVariable String topicId) {
        List<Flashcard> flashcards = flashcardService.getFlashcardsByTopicId(topicId);
        return ResponseEntity.ok(flashcards);
    }
}
