package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Schedule;
import com.example.demo.model.Student;
import com.example.demo.model.Classroom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Find schedules by date (filtered by start time)
    @Query("SELECT s FROM Schedule s WHERE s.startTime >= :startOfDay AND s.startTime < :endOfDay")
    List<Schedule> findByDate(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    // Find schedules by classroom ID
    List<Schedule> findByClassroomId(Long classroomId);

    // Find schedules by student (through SchoolClass)
    @Query("SELECT s FROM Schedule s WHERE :student MEMBER OF s.schoolClass.students")
    List<Schedule> findByStudent(@Param("student") Student student);

    // Find schedules by classroom
    @Query("SELECT s FROM Schedule s WHERE s.classroom = :classroom")
    List<Schedule> findByClassroom(@Param("classroom") Classroom classroom);

    // Find schedules by student (alternative query)
    @Query("SELECT s FROM Schedule s WHERE :student MEMBER OF s.schoolClass.students")
    List<Schedule> findByStudentsContains(@Param("student") Student student);

    // Find schedules by date and classroom
    @Query("SELECT s FROM Schedule s WHERE s.startTime = :startTime AND s.classroom = :classroom")
    List<Schedule> findSchedulesByDateAndClassroom(@Param("startTime") LocalDateTime startTime, @Param("classroom") Classroom classroom);

    // New: Find schedules by start time (finds all schedules starting at a specific time)
    List<Schedule> findByStartTime(LocalDateTime startTime);
    
    // New: Find schedules by date range for a specific classroom
    @Query("SELECT s FROM Schedule s WHERE s.startTime >= :startTime AND s.endTime <= :endTime AND s.classroom = :classroom")
    List<Schedule> findSchedulesByDateRangeAndClassroom(@Param("startTime") LocalDateTime startTime, 
                                                         @Param("endTime") LocalDateTime endTime, 
                                                         @Param("classroom") Classroom classroom);
}
