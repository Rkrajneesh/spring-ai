package com.org.ai.controller;

import com.org.ai.model.TravelPlan;
import com.org.ai.service.RecipeService;
import com.org.ai.service.TravelGuideService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/ai")
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping("/recipe")
    public String generateRecipe(@RequestParam String dish) {
        String draftRecipe= recipeService.getDraftRecipe(dish);
        return recipeService.refineRecipe(draftRecipe);
    }
}
