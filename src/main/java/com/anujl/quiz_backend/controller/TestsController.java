package com.anujl.quiz_backend.controller;

import com.anujl.quiz_backend.dto.SubmitTestRequest;
import com.anujl.quiz_backend.model.SubmitRequest;
import com.anujl.quiz_backend.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/test")
public class TestsController {

    private final TestService testService;

    public TestsController(TestService testService) {
        this.testService = testService;
    }
    
    @GetMapping("/quizzes")
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    
    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable String id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    
    @GetMapping("/quizzes/paged")
    public ResponseEntity<?> getPaginatedQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(testService.getPaginatedQuizzes(page, size));
    }



}
