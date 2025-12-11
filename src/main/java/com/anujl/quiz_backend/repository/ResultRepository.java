package com.anujl.quiz_backend.repository;

import com.anujl.quiz_backend.entity.Result;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultRepository extends MongoRepository<Result, String> {




    @Nullable List<?> findByUserid(String username);
}
