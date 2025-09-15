package com.journeyplanner.journeyplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.journeyplanner.journeyplanner.model.Journey;
import com.journeyplanner.journeyplanner.repository.JourneyRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journeys")
public class JourneyController {

    @Autowired
    private JourneyRepository journeyRepository;

    // Create Journey
    @PostMapping
    public Journey addJourney(@RequestBody Journey journey) {
        return journeyRepository.save(journey);
    }

    // Get All Journeys
    @GetMapping
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    // Get Journey by ID
    @GetMapping("/{id}")
    public Optional<Journey> getJourney(@PathVariable Long id) {
        return journeyRepository.findById(id);
    }

    // Update Journey
    @PutMapping("/{id}")
    public Journey updateJourney(@PathVariable Long id, @RequestBody Journey journey) {
        if (journeyRepository.existsById(id)) {
            journey.setId(id);
            return journeyRepository.save(journey);
        }
        return null;
    }

    // Delete Journey
    @DeleteMapping("/{id}")
    public void deleteJourney(@PathVariable Long id) {
        journeyRepository.deleteById(id);
    }
}
