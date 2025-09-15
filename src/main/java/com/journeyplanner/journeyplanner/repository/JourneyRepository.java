package com.journeyplanner.journeyplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journeyplanner.journeyplanner.model.Journey;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
    // You can define custom queries here if needed later
}
