package com.org.ai.model;

import java.util.List;

public record TravelPlan(String city, Integer days, List<DayPlan> itinerary) {
}
