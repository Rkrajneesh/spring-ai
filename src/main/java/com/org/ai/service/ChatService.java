package com.org.ai.service;

import com.org.ai.tool.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    private final ChatClient chatClient;
    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private WeatherTools weatherTools;

    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chatMessage(String message) {
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

    public String chat(String conversationId, String message) {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String convId = (conversationId == null || conversationId.isBlank()) ? UUID.randomUUID().toString() : conversationId;
        Prompt prompt = new Prompt(List.of(new SystemMessage("You are friendly travel guide, Always suggest 3 attractions and 1 food items.")));
        return chatClient.prompt(prompt).advisors(MessageChatMemoryAdvisor.builder(chatMemory).conversationId(convId).build()) .tools(weatherTools).user(message).call().content();
    }
}
