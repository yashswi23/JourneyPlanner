package com.journeyplanner.journeyplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity   // Marks this class as a JPA entity (maps to a table in MySQL)
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    private Long id;

    private String source;
    private String destination;
    private String date;  
    private String modeOfTravel;

    // Constructors
    public Journey() {}

    public Journey(Long id, String source, String destination, String date, String modeOfTravel) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.modeOfTravel = modeOfTravel;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getModeOfTravel() { return modeOfTravel; }
    public void setModeOfTravel(String modeOfTravel) { this.modeOfTravel = modeOfTravel; }
}
