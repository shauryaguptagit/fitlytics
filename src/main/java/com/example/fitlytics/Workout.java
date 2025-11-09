package com.example.fitlytics;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- NEW
import jakarta.persistence.*; // <-- UPDATED

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer durationInMinutes;
    private Integer caloriesBurned;

    // --- NEW FIELD ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // --- Getters and Setters (for all fields) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(Integer duration) { this.durationInMinutes = duration; }
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Integer calories) { this.caloriesBurned = calories; }

    // Getter/Setter for the new User field
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}