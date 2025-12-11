package com.anujl.quiz_backend.entity;


import com.anujl.quiz_backend.enums.DurationType;
import com.anujl.quiz_backend.model.QuizRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "quizzes")
public class Quiz {

    @Id
    @NotNull
    @NotBlank
    private String id;

    private String title;
    private DurationType duration; // minutes

    private List<QuizRule> rules;  // tag-based rules

    private Boolean isActive;
}
