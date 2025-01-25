package com.lin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lin.entity.Flashcard;
import com.lin.entity.Subject;
import com.lin.entity.Topic;
import com.lin.entity.User;
import com.lin.repository.FlashcardRepository;
import com.lin.repository.SubjectRepository;
import com.lin.repository.TopicRepository;
import com.lin.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@Service
public class ChatbotService {

    @Value("${Chatbot.api.url}")
    private String apiUrl;

    @Value("${Chatbot.api.key}")
    private String apiKey;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final RestTemplate restTemplate;

    public ChatbotService() {
        this.restTemplate = new RestTemplate();
    }

    public String extractPdfContent(MultipartFile file) throws Exception {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }

    }

    public String determineBestTopicFromPdf(MultipartFile file) {
        try {
            // Extract content from the PDF
            String pdfContent = extractPdfContent(file);
            System.out.println("PDF content extracted for topic analysis: " + pdfContent);

            // Prepare request to Gemini API
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Format the request body
            String requestBody = String.format(
                    "{ \"contents\": [ { \"role\": \"user\", \"parts\": [ { \"text\": \"Analyze the following text and suggest the most appropriate topic for it(only 1 to 2 words): %s\" } ] } ] }",
                    pdfContent.replace("\"", "\\\""));

            String finalUrl = apiUrl + "?key=" + apiKey;

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, String.class);

            System.out.println("Response from Gemini: " + response.getBody());

            // Parse the response to extract the topic
            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseNode = mapper.readTree(response.getBody());

                if (responseNode.has("candidates") && responseNode.get("candidates").isArray()) {
                    JsonNode candidatesArray = responseNode.get("candidates");

                    if (candidatesArray.size() > 0) {
                        JsonNode firstCandidate = candidatesArray.get(0);

                        if (firstCandidate.has("content")) {
                            JsonNode contentNode = firstCandidate.get("content");

                            if (contentNode.has("parts") && contentNode.get("parts").isArray()) {
                                JsonNode partsArray = contentNode.get("parts");

                                if (partsArray.size() > 0) {
                                    // Return the suggested topic
                                    return partsArray.get(0).get("text").asText().trim();
                                }
                            }
                        }
                    }
                }

                return "No valid topic found in the response.";
            } else {
                throw new RuntimeException(
                        "Error from Gemini API: " + response.getStatusCodeValue() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while determining the best topic: " + e.getMessage();
        }
    }

    public List<Flashcard> generateFlashcardsFromGemini(String content, int quantity, int difficulty, String subjectId,
            String topicName, String userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // Prepare request body with content to convert into flashcards
            String requestBody = String.format(
                    "{ \"contents\": [ { \"role\": \"user\", \"parts\": [ { \"text\": \"Convert the following text into %d flashcards: %s. The response must be directly. flashcard(number):\\nFront:\\nBack:\" } ] } ] }",
                    quantity, content.replace("\"", "\\\""));

            String finalUrl = apiUrl + "?key=" + apiKey;

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Make API call to Gemini
            ResponseEntity<String> response = restTemplate.exchange(
                    finalUrl, HttpMethod.POST, entity, String.class);

            System.out.println("Response Body: " + response.getBody());

            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseNode = mapper.readTree(response.getBody());

                List<Flashcard> flashcards = new ArrayList<>();

                // Retrieve subject and user from repositories
                Subject subject = mongoTemplate.findById(subjectId, Subject.class);
                User user = mongoTemplate.findById(userId, User.class);

                // Check if the topic exists, otherwise create it
                Topic topic = mongoTemplate.findOne(
                        new Query(Criteria.where("name").is(topicName)),
                        Topic.class);

                if (topic == null) {
                    topic = topicService.createTopicWithDefaultUser(topicName, subjectId);
                }

                // Parse the response to extract flashcards
                if (responseNode.has("candidates") && responseNode.get("candidates").isArray()) {
                    JsonNode candidatesArray = responseNode.get("candidates");

                    if (candidatesArray.size() > 0) {
                        JsonNode firstCandidate = candidatesArray.get(0);

                        if (firstCandidate.has("content")) {
                            JsonNode contentNode = firstCandidate.get("content");

                            if (contentNode.has("parts") && contentNode.get("parts").isArray()) {
                                JsonNode partsArray = contentNode.get("parts");

                                if (partsArray.size() > 0) {
                                    String responseText = partsArray.get(0).get("text").asText().trim();

                                    Pattern flashcardPattern = Pattern.compile(
                                            "flashcard\\(\\d+\\):\\s*Front:\\s*(.*?)\\nBack:\\s*(.*?)(?=\\nflashcard|$)",
                                            Pattern.DOTALL);
                                    Matcher matcher = flashcardPattern.matcher(responseText);

                                    while (matcher.find()) {
                                        String front = matcher.group(1).trim();
                                        String back = matcher.group(2).trim();

                                        Flashcard flashcard = new Flashcard();
                                        flashcard.setQuestion(convertToJson(front)); // Set question as JSON
                                        flashcard.setAnswer(convertToJson(back)); // Set answer as JSON
                                        flashcard.setDifficulty(difficulty);
                                        flashcard.setSubject(subject);
                                        flashcard.setTopic(topic);
                                        flashcard.setUser(user);

                                        flashcards.add(flashcard);

                                        // Save each flashcard
                                        flashcardRepository.save(flashcard);
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("Flashcards generated: " + flashcards.size());

                return flashcards;
            } else {
                throw new RuntimeException(
                        "Error from Gemini API: " + response.getStatusCodeValue() + " - " + response.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate flashcards: " + e.getMessage());
        }
    }

    private String convertToJson(String text) {
        try {
            // Ensure the text ends with a newline
            if (!text.endsWith("\n")) {
                text += "\n";
            }

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("insert", text);
            arrayNode.add(objectNode);
            return arrayNode.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "[]"; // Return empty JSON array in case of failure
        }
    }

    public String getChatbotResponse(String userMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            String requestBody = String.format(
                    "{ \"contents\": [ { \"role\": \"user\", \"parts\": [ { \"text\": \"Convert the following text into flashcards: %s\" } ] } ] }",
                    userMessage.replace("\"", "\\\""));

            String finalUrl = apiUrl + "?key=" + apiKey;

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseNode = mapper.readTree(response.getBody());

                if (responseNode.has("candidates") && responseNode.get("candidates").isArray()) {
                    JsonNode candidatesArray = responseNode.get("candidates");

                    if (candidatesArray.size() > 0) {
                        JsonNode firstCandidate = candidatesArray.get(0);

                        if (firstCandidate.has("content")) {
                            JsonNode contentNode = firstCandidate.get("content");

                            if (contentNode.has("parts") && contentNode.get("parts").isArray()) {
                                JsonNode partsArray = contentNode.get("parts");

                                if (partsArray.size() > 0) {
                                    return partsArray.get(0).get("text").asText().trim().replace("*", "");
                                }
                            }
                        }
                    }
                    return "No valid output found in the response.";
                } else {
                    return "Unexpected response structure: 'candidates' field missing.";
                }
            } else {
                return "Error from API: " + response.getStatusCodeValue() + " - " + response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }
}
