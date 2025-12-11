package com.anujl.quiz_backend.entity;


import com.anujl.quiz_backend.enums.Difficulty;
import com.anujl.quiz_backend.enums.QuestionType;
import com.anujl.quiz_backend.enums.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "questions")
@CompoundIndex(name = "tag_diff_idx", def = "{'tags': 1, 'difficulty': 1}")
public class Question {

    @Id
    @NotNull @NotBlank
    private String id;

    private String question;

    private QuestionType type;

    private List<String> options;    
    private String correctAnswer;



    private Tag tags;

    private Difficulty difficulty;
    private Integer marks;
}
