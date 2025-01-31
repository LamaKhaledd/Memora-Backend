
package com.lin.controller.Question;
import com.lin.entity.Question.Comment;
import com.lin.entity.Question.Question;
import com.lin.entity.Question.Reply;
import com.lin.repository.Question.QuestionRepository;
import com.lin.service.Question.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping
    public ResponseEntity<Question> postQuestion(@RequestBody Question question) {
        Question createdQuestion = questionService.postQuestion(question);
        return ResponseEntity.ok(createdQuestion);

    }

    @PostMapping("/{questionId}/content")
    public ResponseEntity<Question> updateQuestionContent(@PathVariable String questionId, @RequestBody String newContent) {
        Question updatedQuestion = questionService.updateQuestionContent(questionId, newContent);
        return updatedQuestion != null ? ResponseEntity.ok(updatedQuestion) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{questionId}/solve")
    public ResponseEntity<Question> setQuestionSolvedState(@PathVariable String questionId, @RequestParam boolean isSolved) {
        Question updatedQuestion = questionService.setQuestionSolvedState(questionId, isSolved);
        return ResponseEntity.ok(updatedQuestion);
    }
    @PostMapping("/{questionId}/comments/{commentId}/pin")
    public ResponseEntity<Question> pinComment(@PathVariable String questionId,
                                            @PathVariable String commentId,
                                            @RequestParam boolean isPinned) {
        System.out.println("isPinned: " + isPinned);
        System.out.println("commentId: " + commentId);
        System.out.println("questionId: " + questionId);                                       
        Question updatedQuestion = questionService.pinComment(questionId, commentId, isPinned);
        return ResponseEntity.ok(updatedQuestion);
    }



    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Question>> getQuestionsByTag(@PathVariable String tag) {
        List<Question> questions = questionService.getQuestionsByTag(tag);
        return questions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(questions);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Question>> getQuestionsByUserId(@PathVariable String userId) {
        List<Question> questions = questionService.getQuestionsByUserId(userId);
        return ResponseEntity.ok(questions);
    }


    @PostMapping("/{questionId}/comments/{commentId}/")
    public ResponseEntity<Question> addReplyToComment(@PathVariable String questionId,
                                                      @PathVariable String commentId,
                                                      @RequestBody Reply reply) {
        Question updatedQuestion = questionService.addReplyToComment(questionId, commentId, reply);
        return ResponseEntity.ok(updatedQuestion);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable String questionId) {
        return questionService.getQuestionById(questionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{questionId}/comments")
    public ResponseEntity<Question> addCommentToQuestion(@PathVariable String questionId, @RequestBody Comment comment) {
        Question updatedQuestion = questionService.addCommentToQuestion(questionId, comment);
        return ResponseEntity.ok(updatedQuestion);
    }

    public boolean deleteQuestion(String questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            questionRepository.deleteById(questionId);
            return true;
        }
        return false;
    }
    

    @PostMapping("/{questionId}/like")
    public ResponseEntity<Question> likeQuestion(@PathVariable String questionId, @RequestParam String userId) {
        Question updatedQuestion = questionService.likeQuestion(questionId, userId);
        return ResponseEntity.ok(updatedQuestion);
    }
}
