package com.anujl.quiz_backend.dto;

import com.anujl.quiz_backend.model.SubmitRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class  SubmitTestRequest {
    String quizId;
    String username;
    LocalDateTime createdAt=LocalDateTime.now();
    List<SubmitRequest> answers;
}
