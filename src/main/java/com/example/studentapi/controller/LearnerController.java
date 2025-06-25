package com.example.studentapi.controller;

import com.example.studentapi.model.Learner;
import com.example.studentapi.service.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learners")
public class LearnerController {
    @Autowired
    private LearnerService learnerService;

    @GetMapping
    public List<Learner> getAllLearners() {
        return learnerService.getAllLearners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Learner> getLearnerById(@PathVariable Long id) {
        return learnerService.getLearnerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Learner createLearner(@RequestBody Learner learner) {
        return learnerService.saveLearner(learner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Learner> updateLearner(@PathVariable Long id, @RequestBody Learner learner) {
        return learnerService.getLearnerById(id)
                .map(existingLearner -> {
                    learner.setId(id);
                    return ResponseEntity.ok(learnerService.saveLearner(learner));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearner(@PathVariable Long id) {
        return learnerService.getLearnerById(id)
                .map(learner -> {
                    learnerService.deleteLearner(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-department")
    public ResponseEntity<List<Learner>> getLearnersByCourseDepartment(@RequestParam String department) {
        List<Learner> learners = learnerService.getLearnersByCourseDepartment(department);
        return ResponseEntity.ok(learners);
    }
} 