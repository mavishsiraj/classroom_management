package com.example.controller;

import com.example.demo.model.Schedule;
import com.example.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // Get all schedules
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    // Get a schedule by ID
    @GetMapping("/{scheduleId}")
    public Schedule getScheduleById(@PathVariable Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    // Create a new schedule
    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.createSchedule(schedule);
    }

    // Update an existing schedule
    @PutMapping("/{scheduleId}")
    public Schedule updateSchedule(@PathVariable Long scheduleId, @RequestBody Schedule updatedSchedule) {
        return scheduleService.updateSchedule(scheduleId, updatedSchedule);
    }

    // Delete a schedule
    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }

    // Get schedules by date
    @GetMapping("/date/{date}")
    public List<Schedule> getSchedulesByDate(@PathVariable String date) {
        return scheduleService.getSchedulesByDate(date);
    }

    // Assign a classroom to a schedule
    @PostMapping("/{scheduleId}/classroom/{classroomId}")
    public Schedule assignClassroomToSchedule(@PathVariable Long scheduleId, @PathVariable Long classroomId) {
        return scheduleService.assignClassroomToSchedule(scheduleId, classroomId);
    }

    // Enroll a student in a schedule
    @PostMapping("/{scheduleId}/student/{studentId}")
    public Schedule enrollStudentInSchedule(@PathVariable Long scheduleId, @PathVariable Long studentId) {
        return scheduleService.enrollStudentInSchedule(scheduleId, studentId);
    }

    // Remove a student from a schedule
    @DeleteMapping("/{scheduleId}/student/{studentId}")
    public Schedule removeStudentFromSchedule(@PathVariable Long scheduleId, @PathVariable Long studentId) {
        return scheduleService.removeStudentFromSchedule(scheduleId, studentId);
    }
}
