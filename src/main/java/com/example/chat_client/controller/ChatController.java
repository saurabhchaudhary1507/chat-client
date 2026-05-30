package com.example.chat_client.controller;

import com.example.chat_client.model.CountryCities;
import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt(message).call().content();
    }

    @GetMapping("/chat-json")
    public @Nullable ResponseEntity<CountryCities> chatJsonResponse(@RequestParam("message") String message) {
        CountryCities response = chatClient.prompt(message)
                .advisors(SimpleLoggerAdvisor.builder().build()).call()
                .entity(new BeanOutputConverter<>(CountryCities.class));
        return ResponseEntity.ok(response);
    }
}