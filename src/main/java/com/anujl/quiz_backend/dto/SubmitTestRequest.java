package com.anujl.quiz_backend.dto;

import com.anujl.quiz_backend.model.SubmitRequest;
import lombok.Data;

import java.util.List;

@Data
public class  SubmitTestRequest {
    String quizId;
    String username;
    List<SubmitRequest> answers;
}
