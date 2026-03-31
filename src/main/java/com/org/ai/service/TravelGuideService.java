package com.org.ai.service;

import com.org.ai.model.TravelPlan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TravelGuideService {
    private final ChatClient chatClient;

    public TravelGuideService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:prompts/travel-guide.st")
    private Resource travelGuideTemplate;

//    PromptTemplate promptTemplate = new PromptTemplate("Create a {days} day travel plan for {city}.");

    public TravelPlan travelGuideChat(String city,Integer days) {

        PromptTemplate templates = new PromptTemplate(travelGuideTemplate);
        Map<String,Object> params = Map.of("city",city,"days",days);
        Prompt prompt = templates.create(params);
        return chatClient.prompt(prompt).call().entity(TravelPlan.class);
    }
}
