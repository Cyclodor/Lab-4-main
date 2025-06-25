package com.example.studentapi.service;

import com.example.studentapi.model.Course;
import com.example.studentapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private CacheService cacheService;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> getCoursesByNameContaining(String courseName) {
        String cacheKey = "courses_by_name_" + courseName;
        
        if (cacheService.containsKey(cacheKey)) {
            return (List<Course>) cacheService.get(cacheKey);
        }
        
        List<Course> courses = courseRepository.findCoursesByNameContaining(courseName);
        cacheService.put(cacheKey, courses);
        
        return courses;
    }
} 