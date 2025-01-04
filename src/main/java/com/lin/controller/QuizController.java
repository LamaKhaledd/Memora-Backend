package com.lin.controller;

import com.lin.entity.Quiz;
import com.lin.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.ok(createdQuiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable String id) {
        Optional<Quiz> quiz = quizService.getQuizById(id);
        if (quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        } else {
            return ResponseEntity.status(404).body("Quiz not found");
        }
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<?> getQuizByTopicId(@PathVariable String topicId) {
        Optional<Quiz> quiz = quizService.getQuizByTopicId(topicId);
        if (quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        } else {
            return ResponseEntity.status(404).body("Quiz not found");
        }
    }


    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        if (!quizzes.isEmpty()) {
            return ResponseEntity.ok(quizzes);
        } else {
            return ResponseEntity.status(404).body(null);
        
        }
    }
}
