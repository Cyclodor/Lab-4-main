package com.example.studentapi.integration;

import com.example.studentapi.model.Course;
import com.example.studentapi.model.Learner;
import com.example.studentapi.repository.CourseRepository;
import com.example.studentapi.repository.LearnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StudentApiIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateCourseAndLearner_ShouldWorkCorrectly() throws Exception {
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        String courseJson = objectMapper.writeValueAsString(course);

        String courseResponse = mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Java Programming"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Course savedCourse = objectMapper.readValue(courseResponse, Course.class);

        Learner learner = new Learner();
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(savedCourse);

        String learnerJson = objectMapper.writeValueAsString(learner);

        mockMvc.perform(post("/api/learners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(learnerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.course.id").value(savedCourse.getId()));
    }

    @Test
    void testSearchLearnersByDepartment_ShouldReturnLearners() throws Exception {
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");
        Course savedCourse = courseRepository.save(course);

        Learner learner = new Learner();
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(savedCourse);
        learnerRepository.save(learner);

        mockMvc.perform(get("/api/learners/by-department")
                .param("department", "Computer Science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"));
    }

    @Test
    void testSearchCourses_ShouldWorkCorrectly() throws Exception {
        Course course = new Course();
        course.setCourseName("Python Development");
        course.setDepartment("Computer Science");

        String courseJson = objectMapper.writeValueAsString(course);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/courses/search")
                .param("courseName", "Python"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseName").value("Python Development"));
    }

    @Test
    void testCacheOperations_ShouldWorkCorrectly() throws Exception {
        mockMvc.perform(get("/api/cache/info"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/cache/clear"))
                .andExpect(status().isOk());
    }
} 