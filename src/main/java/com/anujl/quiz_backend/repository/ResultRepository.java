package com.anujl.quiz_backend.repository;

import com.anujl.quiz_backend.entity.Result;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Objects;

public interface ResultRepository extends MongoRepository<Result, String> {




    @Nullable List<?> findByUserid(String username);
    Page<Object> findByUserid(String userId, Pageable pageable);
}
