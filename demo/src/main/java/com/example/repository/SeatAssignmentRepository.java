package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.SeatAssignment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface SeatAssignmentRepository extends JpaRepository<SeatAssignment, Long> {

    // Find seat assignments by classroom
    List<SeatAssignment> findByClassroomId(Long classroomId);

    // Find seat assignments by schedule
    List<SeatAssignment> findByScheduleId(Long scheduleId);

    // Find seat assignments by student
    List<SeatAssignment> findByStudentId(Long studentId);

    // Find seat assignments by status
    List<SeatAssignment> findByStatus(String status);

    // Custom query to find seat assignments by classroom and schedule
    @Query("SELECT sa FROM SeatAssignment sa WHERE sa.classroomId = :classroomId AND sa.scheduleId = :scheduleId")
    List<SeatAssignment> findByClassroomAndSchedule(@Param("classroomId") Long classroomId, @Param("scheduleId") Long scheduleId);
}
