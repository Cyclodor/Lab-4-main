package com.example.studentapi.controller;

import com.example.studentapi.model.Course;
import com.example.studentapi.model.Learner;
import com.example.studentapi.service.LearnerService;
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

@WebMvcTest(LearnerController.class)
class LearnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LearnerService learnerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllLearners_ShouldReturnLearners() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        Learner learner = new Learner();
        learner.setId(1L);
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(course);

        List<Learner> learners = Arrays.asList(learner);
        when(learnerService.getAllLearners()).thenReturn(learners);

        mockMvc.perform(get("/api/learners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fullName").value("John Doe"));
    }

    @Test
    void getLearnerById_ShouldReturnLearner() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        Learner learner = new Learner();
        learner.setId(1L);
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(course);

        when(learnerService.getLearnerById(1L)).thenReturn(Optional.of(learner));

        mockMvc.perform(get("/api/learners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    void getLearnerById_ShouldReturnNotFound() throws Exception {
        when(learnerService.getLearnerById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/learners/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createLearner_ShouldReturnCreatedLearner() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        Learner learner = new Learner();
        learner.setId(1L);
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(course);

        when(learnerService.saveLearner(any(Learner.class))).thenReturn(learner);

        mockMvc.perform(post("/api/learners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(learner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    void getLearnersByCourseDepartment_ShouldReturnLearners() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        Learner learner = new Learner();
        learner.setId(1L);
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(course);

        List<Learner> learners = Arrays.asList(learner);
        when(learnerService.getLearnersByCourseDepartment(anyString())).thenReturn(learners);

        mockMvc.perform(get("/api/learners/by-department")
                .param("department", "Computer Science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fullName").value("John Doe"));
    }
} 