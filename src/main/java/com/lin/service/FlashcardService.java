package com.lin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lin.entity.Flashcard;
import com.lin.entity.Subject;
import com.lin.entity.Topic;
import com.lin.repository.FlashcardRepository;
import com.lin.repository.SubjectRepository;
import com.lin.repository.TopicRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {

    private static final Logger logger = LoggerFactory.getLogger(FlashcardService.class);

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;




    public Flashcard updateFlashcardAfterReview(String flashcardId, int responseQuality) {
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Flashcard ID"));

        double easinessFactor = flashcard.getEasinessFactor() != null ? flashcard.getEasinessFactor() : 2.5;
        int interval = flashcard.getInterval() != null ? flashcard.getInterval() : 1;

        if (responseQuality < 3) {
            interval = 1;
        } else {
            interval = (int) Math.ceil(interval * easinessFactor);
            easinessFactor = Math.max(1.3,
                    easinessFactor + (0.1 - (5 - responseQuality) * (0.08 + (5 - responseQuality) * 0.02)));
        }

        flashcard.setEasinessFactor(easinessFactor);
        flashcard.setInterval(interval);
        flashcard.setNextReviewDate(LocalDateTime.now().plusDays(interval));

        return flashcardRepository.save(flashcard);
    }

public String extractPdfContent(MultipartFile file) throws Exception {
    try (PDDocument document = PDDocument.load(file.getInputStream())) {
        PDFTextStripper pdfStripper = new PDFTextStripper();
        return pdfStripper.getText(document);
    }
}

public List<Flashcard> generateFlashcards(String content, int quantity, int difficulty, Subject subject, Topic topic) {
    // Split content into sentences or paragraphs
    String[] sentences = content.split("\\.\\s*");

    List<Flashcard> flashcards = new ArrayList<>();
    for (int i = 0; i < Math.min(quantity, sentences.length); i++) {
        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion("Explain: " + sentences[i].trim());
        flashcard.setAnswer(sentences[i].trim()); // Use same sentence for simplicity
        flashcard.setDifficulty(difficulty);
        flashcard.setSubject(subject);
        flashcard.setTopic(topic);

        flashcards.add(flashcardRepository.save(flashcard));
    }

    return flashcards;
}

    public Flashcard saveFlashcardWithSubjectAndTopic(Flashcard flashcard, int subjectId2, int topicId2) {
        String subjectId = flashcard.getSubject().getId();
        String topicId = flashcard.getTopic().getId();

        logger.info("Saving flashcard with subjectId: " + subjectId + " and topicId: " + topicId);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subjectId: " + subjectId));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid topicId: " + topicId));

        flashcard.setSubject(subject);
        flashcard.setTopic(topic);

        return flashcardRepository.save(flashcard);
    }

    public List<Flashcard> getAllFlashcards() {
        List<Flashcard> flashcards = flashcardRepository.findAll();
        logger.info("Fetched all flashcards: " + flashcards.size() + " flashcards found.");
        return flashcards;
    }

    public Flashcard updateFlashcardFlag(String flashcardId, String flagColor) {
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Flashcard ID"));
    
        flashcard.setFlag(flagColor); // Assuming Flashcard has a field `flagColor`
        return flashcardRepository.save(flashcard);
    }
    

    public List<Flashcard> getFlashcardsByFlag(String flag) {
        logger.info("Fetching flashcards with flag: " + flag);
        return flashcardRepository.findByFlag(flag);
    }
    

    public Optional<Flashcard> getFlashcardById(String id) {
        logger.info("Fetching flashcard by ID: " + id);
        Optional<Flashcard> flashcard = flashcardRepository.findById(id);

        if (!flashcard.isPresent()) {
            logger.warn("Flashcard with ID " + id + " not found.");
        }

        return flashcard;
    }

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return flashcardRepository.save(flashcard);
    }

    public void deleteFlashcard(String id) {
        logger.info("Deleting flashcard with ID: " + id);
        flashcardRepository.deleteById(id);
    }

    public List<Flashcard> getFlashcardsByTopicId(String topicId) {
        return flashcardRepository.findByTopicId(topicId);
    }
}
