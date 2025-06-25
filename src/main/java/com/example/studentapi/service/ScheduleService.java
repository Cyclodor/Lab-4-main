package com.example.studentapi.service;

import com.example.studentapi.dao.ScheduleDAO;
import com.example.studentapi.model.Course;
import com.example.studentapi.dto.BsuirGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleDAO scheduleDAO;
    
    public List<Course> getStudentCourses() {
        return scheduleDAO.getStudentCourses();
    }

    public List<BsuirGroupDto> getBsuirGroups() {
        return scheduleDAO.getBsuirGroups();
    }
} 