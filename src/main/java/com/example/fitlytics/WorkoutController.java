package com.example.fitlytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal; // <-- NEW
import org.springframework.security.core.userdetails.UserDetails; // <-- NEW
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "*") // <-- ADDED
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    // --- NEWLY ADDED ---
    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonApiUrl = "http://localhost:5001/calculate_calories";

    // --- Helper function to get the current user ---
    private User getLoggedInUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // --- UPDATED METHOD ---
    @GetMapping
    public List<Workout> getAllWorkouts(@AuthenticationPrincipal UserDetails userDetails) {
        // Find the logged-in user
        User user = getLoggedInUser(userDetails);
        
        // Only return workouts for that user
        return workoutRepository.findByUserId(user.getId());
    }

    // --- UPDATED METHOD ---
    @PostMapping
    public Workout createWorkout(
            @RequestBody Workout workout,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        // Find the logged-in user
        User user = getLoggedInUser(userDetails);
        
        // Link this workout to the user
        workout.setUser(user);

        // Prepare data for Python API
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("description", workout.getDescription());
        requestBody.put("duration", workout.getDurationInMinutes());

        try {
            CalorieResponse response = restTemplate.postForObject(
                pythonApiUrl, 
                requestBody, 
                CalorieResponse.class
            );
            if (response != null && response.getCaloriesBurned() != null) {
                workout.setCaloriesBurned(response.getCaloriesBurned());
            }
        } catch (Exception e) {
            System.err.println("Error calling Python calorie API: " + e.getMessage());
            workout.setCaloriesBurned(0);
        }

        return workoutRepository.save(workout);
    }

    // --- Helper Class (Unchanged) ---
    private static class CalorieResponse {
        private Integer caloriesBurned;
        public Integer getCaloriesBurned() { return caloriesBurned; }
        public void setCaloriesBurned(Integer calories) { this.caloriesBurned = calories; }
    }
}