package com.org.ai.service;

import com.org.ai.model.TravelPlan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final ChatClient chatClient;

    public RecipeService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getDraftRecipe(String dish) {
        Prompt draftPrompt = new Prompt(new UserMessage("Write a recipe for " + dish + ". Include ingredients and preparation steps."));
        return chatClient.prompt(draftPrompt).call().content();
    }

    public String refineRecipe(String draft) {

        Prompt prompt = new Prompt(new SystemMessage("You are a recipe formatter. Convert recipes into JSON with keys: 'dish, 'ingredients', 'steps'."), new UserMessage("Here is the recipe:\n" + draft));

        return chatClient.prompt(prompt).call().content();
    }
}
