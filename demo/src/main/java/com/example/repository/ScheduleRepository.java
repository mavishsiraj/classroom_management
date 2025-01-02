package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Schedule;
import com.example.demo.model.Student;
import com.example.demo.model.Classroom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Find schedules by date
    List<Schedule> findByDate(LocalDate date);

    // Find schedules by classroom ID
    List<Schedule> findByClassroomId(Long classroomId);

    // Find schedules by student ID
    List<Schedule> findByStudentId(Long studentId);

    // Custom query to find schedules by classroom
    @Query("SELECT s FROM Schedule s WHERE s.classroom = :classroom")
    List<Schedule> findByClassroom(@Param("classroom") Classroom classroom);

    // Custom query to find schedules by student
    @Query("SELECT s FROM Schedule s WHERE :student MEMBER OF s.students")
    List<Schedule> findByStudentsContains(@Param("student") Student student);

    // Custom query to find schedules by date and classroom
    @Query("SELECT s FROM Schedule s WHERE s.date = :date AND s.classroom = :classroom")
    List<Schedule> findSchedulesByDateAndClassroom(@Param("date") LocalDate date, @Param("classroom") Classroom classroom);
}
