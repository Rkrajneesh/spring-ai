package com.org.ai.controller;

import com.org.ai.service.TravelGuideService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
class TravelGuideController {

    private final TravelGuideService travelGuideService;
    public TravelGuideController(TravelGuideService travelGuideService) {
        this.travelGuideService = travelGuideService;
    }
    @GetMapping("/travel-guide")
    public String prepareTravelGuide(@RequestParam String city, @RequestParam Integer days) {
        return travelGuideService.travelGuideChat(city,days);
    }
}
