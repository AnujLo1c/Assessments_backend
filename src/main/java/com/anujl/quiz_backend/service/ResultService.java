package com.anujl.quiz_backend.service;

import com.anujl.quiz_backend.dto.SubmitTestRequest;
import com.anujl.quiz_backend.entity.Question;
import com.anujl.quiz_backend.entity.Result;
import com.anujl.quiz_backend.model.SubmitRequest;
import com.anujl.quiz_backend.repository.QuestionRepository;
import com.anujl.quiz_backend.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {

    final ResultRepository resultRepository;
    final JwtService jwtService;
    final QuestionRepository questionRepository;
    final UserService userService;
    public Object getAllResults() {
return resultRepository.findAll();
    }

    public Object getResultById(String id) {
        return resultRepository.findById(id).orElse(null);
    }

    public String submitTest(SubmitTestRequest submitRequest) {

        List<SubmitRequest> answers = submitRequest.getAnswers();
        System.out.println(answers);
        List<SubmitRequest> validAnswers = answers.stream()
                .filter(Objects::nonNull)
                .toList();

        List<String> questionIds = validAnswers.stream()
                .map(SubmitRequest::getQuestionId)
                .toList();

        Map<String, Question> questionMap = questionRepository.findAllById(questionIds)
                .stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        int score = 0;
        int totalMarks = 0;
        int attempted = 0;
        int correct = 0;
        int totalQuestions = answers.size();
        for (SubmitRequest s : validAnswers) {

            Question q = questionMap.get(s.getQuestionId());
            if (q == null || s.getSelected()==null) continue; // safety

            attempted++;
            totalMarks += q.getMarks();

            if (q.getCorrectAnswer().equalsIgnoreCase(s.getSelected())) {
                score += q.getMarks();
                correct++;
            }
        }


        // 5️⃣ Build Result object
        Result result = Result.builder()
                .userid(submitRequest.getUsername())
                .quizid(submitRequest.getQuizId())
                .score(score)
                .total(totalMarks)
                .totalQuestions(totalQuestions)
                .correctAnswers(correct)
                .totalAttempted(attempted)

                .build();

        System.out.println("Resutl "+result);
        System.out.println("Resutl "+submitRequest.getUsername());
        Result saved = resultRepository.save(result);

        return saved.getId();
    }

    public @Nullable List<?> getUserResults(String username) {
//        String userId = userService.getUserIdByUsername(username);
        return resultRepository.findByUserid(username);

    }
}
