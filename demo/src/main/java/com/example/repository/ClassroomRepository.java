package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Classroom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    // Find classrooms by name
    List<Classroom> findByName(String name);

    // Find classrooms by capacity greater than a specified value
    List<Classroom> findByCapacityGreaterThan(int capacity);

    // Find available classrooms
    List<Classroom> findByIsAvailable(boolean isAvailable);

    // Custom query to find available classrooms with capacity greater than a specified value
    @Query("SELECT c FROM Classroom c WHERE c.capacity > :capacity AND c.isAvailable = true")
    List<Classroom> findAvailableClassroomsWithCapacityGreaterThan(@Param("capacity") int capacity);
}
