package com.example.totalDuration.repository;

import com.example.totalDuration.model.YearlyTrainingSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YearlyTrainingRepository extends MongoRepository<YearlyTrainingSummary, String> {
}
