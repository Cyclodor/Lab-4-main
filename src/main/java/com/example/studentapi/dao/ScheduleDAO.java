package com.example.studentapi.dao;

import com.example.studentapi.model.Course;
import com.example.studentapi.dto.BsuirGroupDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class ScheduleDAO {
    private final RestTemplate restTemplate;

    public ScheduleDAO() {
        this.restTemplate = new RestTemplate();
    }

    public List<Course> getStudentCourses() {
        String url = "https://iis.bsuir.by/api/v1/student-groups";
        try {
            Course[] courses = restTemplate.getForObject(url, Course[].class);
            return Arrays.asList(courses);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<BsuirGroupDto> getBsuirGroups() {
        String url = "https://iis.bsuir.by/api/v1/student-groups";
        try {
            BsuirGroupDto[] groups = restTemplate.getForObject(url, BsuirGroupDto[].class);
            return Arrays.asList(groups);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}