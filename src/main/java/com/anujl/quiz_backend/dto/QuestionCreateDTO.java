package com.anujl.quiz_backend.dto;

import com.anujl.quiz_backend.enums.Difficulty;
import com.anujl.quiz_backend.enums.QuestionType;
import com.anujl.quiz_backend.enums.Tag;
import lombok.Data;
import java.util.List;

@Data
public class QuestionCreateDTO {
    private String question;
    private QuestionType type;
    private List<String> options;
    private String correctAnswer;
 ;
    private Tag tags;
    private Difficulty difficulty;
    private Integer marks;
}
