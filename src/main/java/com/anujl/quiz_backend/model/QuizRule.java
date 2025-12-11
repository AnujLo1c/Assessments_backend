package com.anujl.quiz_backend.model;

import com.anujl.quiz_backend.enums.Difficulty;
import com.anujl.quiz_backend.enums.Tag;
import lombok.Data;

@Data
public class QuizRule {
    private Tag tag;
    private Difficulty difficulty;  
    private Integer count;      
}
