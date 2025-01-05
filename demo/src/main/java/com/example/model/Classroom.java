package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "classrooms")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classroomId;

    @NotBlank(message = "Classroom name cannot be blank")
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 500, message = "Capacity cannot exceed 500")
    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int occupiedSeats = 0; // Tracks the number of occupied seats.

    @Size(max = 255, message = "Resources description cannot exceed 255 characters")
    @Column(length = 255)
    private String resources;

    @Column(nullable = false)
    private boolean available = true;

    /**
     * Default Constructor.
     */
    public Classroom() {
    }

    // Getters and Setters
    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        if (occupiedSeats <= this.capacity) {
            this.occupiedSeats = occupiedSeats;
        } else {
            throw new IllegalArgumentException("Occupied seats cannot exceed capacity.");
        }
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Method to get the number of available seats.
     * @return the number of seats currently available.
     */
    public int getAvailableSeats() {
        return capacity - occupiedSeats;
    }

    /**
     * Method to set the number of available seats.
     * This will update the occupiedSeats accordingly.
     * @param availableSeats the new number of available seats.
     */
    public void setAvailableSeats(int availableSeats) {
        int newOccupiedSeats = this.capacity - availableSeats;
        if (newOccupiedSeats <= this.capacity && newOccupiedSeats >= 0) {
            this.occupiedSeats = newOccupiedSeats;
        } else {
            throw new IllegalArgumentException("Invalid available seats value.");
        }
    }
}