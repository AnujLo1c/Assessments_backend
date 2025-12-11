package com.anujl.quiz_backend.controller;

import com.anujl.quiz_backend.dto.QuestionCreateDTO;

import com.anujl.quiz_backend.dto.QuizCreateDTO;
import com.anujl.quiz_backend.entity.Question;
import com.anujl.quiz_backend.entity.Quiz;
import com.anujl.quiz_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    

    
    @PostMapping("/quiz/add")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizCreateDTO quizDTO) {
        return ResponseEntity.ok(adminService.addQuiz(quizDTO));
    }

    
    @GetMapping("/quiz")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(adminService.getAllQuizzes());
    }

    
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable String quizId) {
        return ResponseEntity.ok(adminService.getQuizById(quizId));
    }

    
    @PutMapping("/quiz/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(
            @PathVariable String quizId,
            @RequestParam String title
    ) {
        return ResponseEntity.ok(adminService.updateQuizTitle(quizId, title));
    }

    
    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable String quizId) {
        adminService.deleteQuiz(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

    @PostMapping("/question/add")
    public ResponseEntity<Question> addQuestion(

            @RequestBody QuestionCreateDTO dto
    ) {
        return ResponseEntity.ok(adminService.addQuestion( dto));
    }
    @PostMapping("/question/add_all")
    public ResponseEntity<String> addAllQuestion(

            @RequestBody List<QuestionCreateDTO> dto
    ) {
        adminService.addAllQuestion( dto);
        return ResponseEntity.ok().body("done");
    }



    @GetMapping("/question")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(adminService.getAllQuestions());
    }

    
    @GetMapping("/question/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable String questionId) {
        return ResponseEntity.ok(adminService.getQuestionById(questionId));
    }

    
    @PutMapping("/question/{questionId}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable String questionId,
            @RequestBody QuestionCreateDTO dto
    ) {
        return ResponseEntity.ok(adminService.updateQuestion(questionId, dto));
    }

    
    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable String questionId
    ) {
        adminService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question deleted successfully");
    }
}
