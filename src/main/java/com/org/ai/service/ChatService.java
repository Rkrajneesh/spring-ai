package com.org.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatClient chatClient;
    private Prompt prompt;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chatMessage(String message){
//        1. Simpler
//    return chatClient.prompt().user(message).call().content();

//        2. With System and user message.
//        return chatClient.prompt().system("You are an experienced spring boot trainer. Always explain step by step in bullet points.").user(message).call().content();

//        Prompt with message list.
        Prompt prompt = new Prompt(List.of(new SystemMessage("You are friendly travel guide, Always suggest 3 attractions and 1 food items."),
                new UserMessage("Plan my day in Rome."),
                new AssistantMessage("Morning: Visit the Collosseum\\nAfternoon: Explore the Vatican Museum\\nEvening: See the Trevi Fountain. \\nFood: gelato.")));
        return chatClient.prompt(prompt).user(message).call().content();
    }
}
