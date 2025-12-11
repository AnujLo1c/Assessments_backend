package com.anujl.quiz_backend.controller;

import com.anujl.quiz_backend.dto.SubmitTestRequest;
import com.anujl.quiz_backend.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/result")
@RequiredArgsConstructor
public class ResultController {
final ResultService resultService;
    @PostMapping("/submit")
    public ResponseEntity<?> submitTest(@RequestBody SubmitTestRequest submitRequest) {
        return ResponseEntity.ok().body(Map.of("resultId",resultService.submitTest(submitRequest)));
    }


    @GetMapping("/results")
    public ResponseEntity<?> getAllResults() {
        return ResponseEntity.ok(resultService.getAllResults());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserResults(@PathVariable("id") String id) {
        return ResponseEntity.ok(resultService.getUserResults(id));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getResultById(@PathVariable String id) {
        return ResponseEntity.ok(resultService.getResultById(id));
    }
}
