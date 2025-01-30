package com.lin.service.Question;

import com.lin.entity.Question.Comment;
import com.lin.entity.Question.Question;
import com.lin.entity.Question.Reply;
import com.lin.repository.Question.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;


    public Question updateQuestionContent(String questionId, String newContent) {
        return questionRepository.findById(questionId).map(question -> {
            question.setContent(newContent);
            question.setUpdatedAt(LocalDateTime.now().toString());
            return questionRepository.save(question);
        }).orElse(null);
    }

    public Question postQuestion(Question question) {
        question.setLikesCount(0);
        question.setCommentsCount(0);
        question.setShared(false);
        return questionRepository.save(question);
    }



    public Question addReplyToComment(String questionId, String commentId, Reply reply) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            Optional<Comment> commentOpt = question.getComments().stream()
                    .filter(c -> c.getCommentId().equals(commentId))
                    .findFirst();
            
            if (commentOpt.isPresent()) {
                Comment comment = commentOpt.get();
                comment.getReplies().add(reply);
                return questionRepository.save(question);
            } else {
                throw new RuntimeException("Comment not found");
            }
        } else {
            throw new RuntimeException("Question not found");
        }
    }



    public Question setQuestionSolvedState(String questionId, boolean isSolved) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            question.setSolved(isSolved);  // Update the solved state
            return questionRepository.save(question);  // Save the updated question
        } else {
            throw new RuntimeException("Question not found");
        }
    }


    public Question pinComment(String questionId, String commentId, boolean isPinned) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            Optional<Comment> commentOpt = question.getComments().stream()
                    .filter(c -> c.getCommentId().equals(commentId))
                    .findFirst();
            
            if (commentOpt.isPresent()) {
                Comment comment = commentOpt.get();
                comment.setPinned(isPinned);
                return questionRepository.save(question);
            } else {
                throw new RuntimeException("Comment not found");
            }
        } else {
            throw new RuntimeException("Question not found");
        }
    }
    


    

    public List<Question> getQuestionsByTag(String tag) {
        return questionRepository.findByTagsContaining(tag);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(String questionId) {
        return questionRepository.findById(questionId);
    }

    
    public List<Question> getQuestionsByUserId(String userId) {
        return questionRepository.findByUserId(userId);
    }
    

    public Question addCommentToQuestion(String questionId, Comment comment) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            question.getComments().add(comment);
            question.setCommentsCount(question.getCommentsCount() + 1);
            return questionRepository.save(question);
        } else {
            throw new RuntimeException("Question not found");
        }
    }

    public Question likeQuestion(String questionId, String userId) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            if (!question.getLikedByUsers().contains(userId)) {
                question.getLikedByUsers().add(userId);
                question.setLikesCount(question.getLikesCount() + 1);
                return questionRepository.save(question);
            } else {
                throw new RuntimeException("User already liked this question");
            }
        } else {
            throw new RuntimeException("Question not found");
        }
    }
}
