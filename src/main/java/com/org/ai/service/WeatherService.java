package com.org.ai.service;

import com.org.ai.tool.ContactsTool;
import com.org.ai.tool.WeatherTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    @Autowired private WeatherTools weatherTools;
    @Autowired private ContactsTool contactsTool;

    public String chat(String conversationId, String message) {
        // generate id if not passed
        String convId = (conversationId == null || conversationId.isBlank()) ? UUID.randomUUID().toString(): conversationId;
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        Prompt prompt = new Prompt(List.of(
                new SystemMessage("Today’s date is " + today + ". " +
                        "You are a friendly travel guide. Suggest 3 attractions and 1 food item." )
        ));
        return chatClient.prompt(prompt).advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(convId).build()).tools(weatherTools, contactsTool)
                        .user(message).call().content();
    }

}