package com.lin.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.util.Collections;

@Service
public class TranslationService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL = "https://api.lecto.ai/v1/translate/text";
    private final String API_KEY = "XWQDCHQ-XFHMM88-KJK2HD3-EX954AH"; // Your API Key
    public String translateText(String text, String fromLanguage, String toLanguage) {
        try {
            // Clean the text to remove any unwanted newline or special characters
            text = text.replaceAll("\\n", " ").replaceAll("\"", "\\\"");
    
            // Set up the request payload
            String requestBody = String.format(
                "{\"texts\":[\"%s\"],\"from\":\"%s\",\"to\":[\"%s\"]}",
                text, fromLanguage, toLanguage);
    
            // Debugging: Print the request body
            System.out.println("Request Body: " + requestBody);
    
            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("X-API-Key", API_KEY);
    
            // Prepare the request entity
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    
            // Debugging: Log headers
            System.out.println("Request Headers: " + headers);
    
            // Send POST request to the API
            System.out.println("Sending request to API...");
            ResponseEntity<String> response = restTemplate.exchange(
                API_URL, HttpMethod.POST, entity, String.class);
    
            // Debugging: Log the response status and body
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
    
            // Parse response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseBody = objectMapper.readTree(response.getBody());
    
            // Debugging: Print the raw response body
            System.out.println("Raw Response Body: " + responseBody.toString());
    
            // Extract translated text from the 'translations' array inside 'translations'
            String translatedText = responseBody.get("translations")
                                                 .get(0) // Access the first translation object
                                                 .get("translated") // Access the 'translated' array
                                                 .get(0) // Get the first element in the array
                                                 .asText(); // Extract text
    
            // Debugging: Print the final translated text
            System.out.println("Translated Text: " + translatedText);
    
            // Return the translated text
            return translatedText;
    
        } catch (Exception e) {
            e.printStackTrace();
            // Debugging: Print error message
            System.out.println("Error during translation: " + e.getMessage());
            return "Translation failed";
        }
    }
}    