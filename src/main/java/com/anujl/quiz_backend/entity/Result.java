package com.anujl.quiz_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "results")
@CompoundIndex(name = "results_idx", def = "{'userid': 1}")
public class Result {

    @Id
    private String id;
    private String userid;//this is actuall username
    private String quizid;
    private Integer score;
    private Integer total;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer totalAttempted;
    private LocalDateTime createdAt;
}
