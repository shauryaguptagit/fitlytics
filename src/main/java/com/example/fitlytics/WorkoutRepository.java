package com.example.fitlytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // <-- NEW

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    // --- NEW METHOD ---
    List<Workout> findByUserId(Long userId);
}