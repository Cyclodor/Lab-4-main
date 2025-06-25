package com.example.studentapi.repository;

import com.example.studentapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %:courseName%")
    List<Course> findCoursesByNameContaining(@Param("courseName") String courseName);
} 