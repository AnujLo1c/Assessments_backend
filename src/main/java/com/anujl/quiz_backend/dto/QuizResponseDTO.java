package com.anujl.quiz_backend.dto;

import com.anujl.quiz_backend.enums.DurationType;
import com.anujl.quiz_backend.model.QuizRule;
import lombok.Data;
import java.util.List;

@Data
public class QuizResponseDTO {
    private String id;
    private String title;
    private DurationType duration;
    private List<QuizRule> rules;
}
