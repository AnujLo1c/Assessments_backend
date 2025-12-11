package com.anujl.quiz_backend.repository;

import com.anujl.quiz_backend.entity.Question;
import com.anujl.quiz_backend.enums.Difficulty;
import com.anujl.quiz_backend.enums.Tag;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends  MongoRepository<Question, String> {
    @Aggregation(pipeline = {
            "{ $match: { tags: ?0, difficulty: ?1 } }",
            "{ $sample: { size: ?2 } }"
    })
    List<Question> findRandomByTagAndDifficulty(Tag tag, Difficulty difficulty, Integer count);
}
