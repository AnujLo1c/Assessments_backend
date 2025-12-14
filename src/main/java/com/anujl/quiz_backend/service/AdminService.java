package com.anujl.quiz_backend.service;

import com.anujl.quiz_backend.dto.QuestionCreateDTO;

import com.anujl.quiz_backend.dto.QuizCreateDTO;
import com.anujl.quiz_backend.entity.Question;
import com.anujl.quiz_backend.entity.Quiz;
import com.anujl.quiz_backend.repository.QuestionRepository;
import com.anujl.quiz_backend.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;

    public AdminService(QuizRepository quizRepo, QuestionRepository questionRepo) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
    }

    

    public Quiz addQuiz(QuizCreateDTO dto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setDuration(dto.getDuration());
        quiz.setRules(dto.getRules());
        return quizRepo.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepo.findAll();
    }

    public Quiz getQuizById(String quizId) {
        return quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));
    }

    public Quiz updateQuizTitle(String quizId, String title) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));

        quiz.setTitle(title);
        return quizRepo.save(quiz);
    }

    public void deleteQuiz(String quizId) {
        if (!quizRepo.existsById(quizId)) {
            throw new RuntimeException("Quiz not found: " + quizId);
        }
        quizRepo.deleteById(quizId);
    }


    

    public Question addQuestion(QuestionCreateDTO dto) {

        Question q = new Question();
        q.setQuestion(dto.getQuestion());
        q.setOptions(dto.getOptions());
        q.setCorrectAnswer(dto.getCorrectAnswer());
        q.setDifficulty(dto.getDifficulty());
        q.setTags(dto.getTags());
        q.setType(dto.getType());
        q.setMarks(dto.getMarks());

        return questionRepo.save(q);
    }
@Transactional
    public void addAllQuestion(List<QuestionCreateDTO> allDto) {
allDto.forEach(dto-> {
    Question q = new Question();
    q.setQuestion(dto.getQuestion());
    q.setOptions(dto.getOptions());
    q.setCorrectAnswer(dto.getCorrectAnswer());
    q.setDifficulty(dto.getDifficulty());
    q.setTags(dto.getTags());
    q.setType(dto.getType());
    q.setMarks(dto.getMarks());

    questionRepo.save(q);
});
        System.out.println("Added all questions");
    }

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public Question getQuestionById(String questionId) {
        return questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found: " + questionId));
    }

    public Question updateQuestion(String questionId, QuestionCreateDTO dto) {
        Question q = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found: " + questionId));

        q.setQuestion(dto.getQuestion());
        q.setOptions(dto.getOptions());
        q.setCorrectAnswer(dto.getCorrectAnswer());
        q.setDifficulty(dto.getDifficulty());
        q.setTags(dto.getTags());
        q.setType(dto.getType());
        q.setMarks(dto.getMarks());

        return questionRepo.save(q);
    }

    public void deleteQuestion(String questionId) {
        if (!questionRepo.existsById(questionId)) {
            throw new RuntimeException("Question not found: " + questionId);
        }
        questionRepo.deleteById(questionId);
    }
}
