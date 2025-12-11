package com.anujl.quiz_backend.model;

import lombok.Data;


@Data
public class SubmitRequest {
    String questionId;
    String selected;
}
