package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find students by name
    List<Student> findByName(String name);

    // Find students within a specific age range
    List<Student> findByAgeBetween(int startAge, int endAge);

    // Find a student by email
    Student findByEmail(String email);

    // Find students by status (e.g., Active, Inactive)
    List<Student> findByStatus(String status);

    // Custom query to search students by name or email
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.email LIKE %:keyword%")
    List<Student> searchByNameOrEmail(@Param("keyword") String keyword);
}
