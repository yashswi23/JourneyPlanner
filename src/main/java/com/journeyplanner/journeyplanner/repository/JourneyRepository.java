package com.journeyplanner.journeyplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journeyplanner.journeyplanner.model.Journey;
import java.util.List;   // ✅ Correct import

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
    // Custom query method
    List<Journey> findBySource(String source);
    List<Journey> findByDestination(String destination);
}
