package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Classroom;
import com.example.demo.model.Schedule;
import com.example.demo.model.Student;
import com.example.repository.ClassroomRepository;
import com.example.repository.ScheduleRepository;
import com.example.repository.StudentRepository;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Get all schedules
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Create a new schedule
    public Schedule createSchedule(Schedule schedule) {
        // Validate schedule data (e.g., check for null fields, conflicting times, etc.)
        if (schedule.getClassroom() == null) {
            throw new RuntimeException("Classroom cannot be null for a schedule");
        }
        return scheduleRepository.save(schedule);
    }

    // Assign a classroom to a schedule
    public Schedule assignClassroomToSchedule(Long scheduleId, Long classroomId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        // Check classroom availability
        if (classroom.getAvailableSeats() <= 0) {
            throw new RuntimeException("Classroom is full");
        }

        schedule.setClassroom(classroom);
        return scheduleRepository.save(schedule);
    }

    // Enroll a student in a schedule
    public Schedule enrollStudentInSchedule(Long scheduleId, Long studentId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if the student is already enrolled
        if (schedule.getStudents().contains(student)) {
            throw new RuntimeException("Student is already enrolled in this schedule");
        }

        // Ensure classroom capacity is not exceeded
        Classroom classroom = schedule.getClassroom();
        if (classroom != null && classroom.getAvailableSeats() <= 0) {
            throw new RuntimeException("No available seats in the classroom");
        }

        schedule.getStudents().add(student);

        // Decrease available seats in the classroom
        if (classroom != null) {
            classroom.setAvailableSeats(classroom.getAvailableSeats() - 1);
            classroomRepository.save(classroom);
        }

        return scheduleRepository.save(schedule);
    }

    // Remove a student from a schedule
    public Schedule removeStudentFromSchedule(Long scheduleId, Long studentId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!schedule.getStudents().remove(student)) {
            throw new RuntimeException("Student was not enrolled in this schedule");
        }

        // Increase available seats in the classroom
        Classroom classroom = schedule.getClassroom();
        if (classroom != null) {
            classroom.setAvailableSeats(classroom.getAvailableSeats() + 1);
            classroomRepository.save(classroom);
        }

        return scheduleRepository.save(schedule);
    }

    // Get all schedules for a specific classroom
    public List<Schedule> getSchedulesByClassroom(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        return scheduleRepository.findByClassroom(classroom);
    }

    // Get all schedules for a specific student
    public List<Schedule> getSchedulesByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return scheduleRepository.findByStudentsContains(student);
    }

    // Assign seats to a schedule (for classroom management)
    public Schedule assignSeatsToSchedule(Long scheduleId, int seatsToAssign) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        Classroom classroom = schedule.getClassroom();
        if (classroom == null) {
            throw new RuntimeException("No classroom assigned to this schedule");
        }

        if (classroom.getAvailableSeats() < seatsToAssign) {
            throw new RuntimeException("Not enough available seats in the classroom");
        }

        classroom.setAvailableSeats(classroom.getAvailableSeats() - seatsToAssign);
        classroomRepository.save(classroom);

        return schedule;
    }
}
