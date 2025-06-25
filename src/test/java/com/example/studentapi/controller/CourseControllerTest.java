package com.example.studentapi.controller;

import com.example.studentapi.model.Course;
import com.example.studentapi.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCourses_ShouldReturnCourses() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        List<Course> courses = Arrays.asList(course);
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].courseName").value("Java Programming"));
    }

    @Test
    void getCourseById_ShouldReturnCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseName").value("Java Programming"));
    }

    @Test
    void getCourseById_ShouldReturnNotFound() throws Exception {
        when(courseService.getCourseById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/courses/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        when(courseService.saveCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courseName").value("Java Programming"));
    }

    @Test
    void getCoursesByNameContaining_ShouldReturnCourses() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        List<Course> courses = Arrays.asList(course);
        when(courseService.getCoursesByNameContaining(anyString())).thenReturn(courses);

        mockMvc.perform(get("/api/courses/search")
                .param("courseName", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].courseName").value("Java Programming"));
    }
} 