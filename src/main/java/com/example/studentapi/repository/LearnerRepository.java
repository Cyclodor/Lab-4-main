package com.example.studentapi.repository;

import com.example.studentapi.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    
    @Query("SELECT l FROM Learner l JOIN l.course c WHERE c.department = :department")
    List<Learner> findLearnersByCourseDepartment(@Param("department") String department);
} 