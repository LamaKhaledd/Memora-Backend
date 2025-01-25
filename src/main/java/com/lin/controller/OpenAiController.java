package com.lin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lin.entity.ChatCompletionRequest;
import com.lin.entity.ChatCompletionResponse;

@RestController
public class OpenAiController {
    
    @Autowired
    RestTemplate restTemplate;
    @PostMapping("/openai")
    public String openAi(@RequestBody String prompt) {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-3.5-turbo", prompt);
        ChatCompletionResponse chatCompletionResponse = restTemplate.postForObject("https://api.openai.com/v1/chat/completions", chatCompletionRequest, ChatCompletionResponse.class);
       return chatCompletionResponse.getChoices().get(0).getMessage().getContent();
    }
}
