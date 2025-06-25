package com.example.studentapi.controller;

import com.example.studentapi.model.Course;
import com.example.studentapi.service.ScheduleService;
import com.example.studentapi.dto.BsuirGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-groups")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;
    
    @GetMapping
    public ResponseEntity<List<Course>> getStudentGroups() {
        List<Course> courses = scheduleService.getStudentCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/bsuir-groups")
    public ResponseEntity<List<BsuirGroupDto>> getBsuirGroups() {
        List<BsuirGroupDto> groups = scheduleService.getBsuirGroups();
        return ResponseEntity.ok(groups);
    }
} 