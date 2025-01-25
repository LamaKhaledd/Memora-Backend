package com.lin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lin.entity.Flashcard;
import com.lin.service.ChatbotService;

@Controller
public class ChatController {

    @Autowired
    private ChatbotService chatbotService;

    @GetMapping("/")
    public String index() {
        return "chat";
    }

    @ResponseBody
    @GetMapping("/api/chat")
    public String getResponse(@RequestParam String userInput) {
        return chatbotService.getChatbotResponse(userInput);
    }


    @PostMapping("/api/determine-topic")
    @ResponseBody
    public ResponseEntity<String> determineBestTopic(@RequestParam("file") MultipartFile file) {
        try {
            String topic = chatbotService.determineBestTopicFromPdf(file);
            return ResponseEntity.ok(topic);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to determine the topic.");
        }
    }



    @PostMapping("/api/generate-flashcards")
    @ResponseBody
    public ResponseEntity<List<Flashcard>> generateFlashcardsFromPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam int quantity,
            @RequestParam int difficulty,
            @RequestParam String subjectId,
            @RequestParam String topicId,
            @RequestParam String userId) {
    
        try {
            System.out.println("Generating flashcards...");
            // Extract content from the PDF file
            String pdfContent = chatbotService.extractPdfContent(file);
            System.out.println("PDF content extracted: " + pdfContent);

            System.out.println("Generating flashcards Lama weweeeeeeeeeeeeee");

            // Send content to Gemini and get flashcard data
            List<Flashcard> flashcards = chatbotService.generateFlashcardsFromGemini(
                    pdfContent, quantity, difficulty, subjectId, topicId, userId);
    
            System.out.println("Flashcards generated: " + flashcards.size());
            return ResponseEntity.ok(flashcards);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

}
