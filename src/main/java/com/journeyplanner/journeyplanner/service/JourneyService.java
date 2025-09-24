// package com.journeyplanner.journeyplanner.service;

// import com.journeyplanner.journeyplanner.model.Journey;
// import com.journeyplanner.journeyplanner.repository.JourneyRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class JourneyService {

//     private final JourneyRepository journeyRepository;

//     public JourneyService(JourneyRepository journeyRepository) {
//         this.journeyRepository = journeyRepository;
//     }

//     // ✅ Save Journey
//     public Journey saveJourney(Journey journey) {
//         return journeyRepository.save(journey);
//     }

//     // ✅ Get All Journeys
//     public List<Journey> getAllJourneys() {
//         return journeyRepository.findAll();
//     }

//     // ✅ Get Journey by ID
//     public Optional<Journey> getJourneyById(Long id) {
//         return journeyRepository.findById(id);
//     }

//     // ✅ Delete Journey
//     public void deleteJourney(Long id) {
//         journeyRepository.deleteById(id);
//     }

//     // ✅ Search by Source
//     public List<Journey> searchBySource(String source) {
//         return journeyRepository.findBySource(source);
//     }
// }
