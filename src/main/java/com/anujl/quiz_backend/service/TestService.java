package com.anujl.quiz_backend.service;

import com.anujl.quiz_backend.dto.QuestionResponseDTO;
import com.anujl.quiz_backend.dto.QuizResponseDTO;
import com.anujl.quiz_backend.dto.SubmitTestRequest;
import com.anujl.quiz_backend.model.SubmitRequest;
import com.anujl.quiz_backend.entity.Question;
import com.anujl.quiz_backend.entity.Quiz;
import com.anujl.quiz_backend.entity.Result;
import com.anujl.quiz_backend.model.QuizRule;
import com.anujl.quiz_backend.repository.QuestionRepository;
import com.anujl.quiz_backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {


    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private  final ModelMapper modelMapper;
    

    
    public List<QuizResponseDTO> getAllTests() {
        return quizRepository.findAll().stream().map(m->modelMapper.map(m, QuizResponseDTO.class)).toList();
    }

    //
    public List<?> getTestById(String id) {
        List<QuestionResponseDTO> allQuestions = new ArrayList<>();
Quiz quiz=quizRepository.findById(id).orElseThrow(() -> new RuntimeException("id not present"));
        for (QuizRule rule : quiz.getRules()) {
            List<Question> random = questionRepository.findRandomByTagAndDifficulty(
                    rule.getTag(),
                    rule.getDifficulty(),
                    rule.getCount()
            );

            allQuestions.addAll(random.stream().map(m->modelMapper.map(m,QuestionResponseDTO.class)).toList());
        }
        System.out.println(allQuestions);
        return allQuestions;
    }

    
    public Page<Quiz> getPaginatedQuizzes(int page, int size) {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;

        Pageable pageable = PageRequest.of(page, size);
        return quizRepository.findAll(pageable);
    }

    


}
