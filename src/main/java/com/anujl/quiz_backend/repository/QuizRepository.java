package com.anujl.quiz_backend.repository;

import com.anujl.quiz_backend.entity.Quiz;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
}
