package com.example.totalDuration.repository;

import com.example.totalDuration.model.YearlyTrainingSummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

public interface YearlySummaryRepository extends CrudRepository<YearlyTrainingSummary, Long> {
    Optional<YearlyTrainingSummary> findByTrainerSummaryUserNameAndTrainingYear(String trainerUsername, Integer trainingYear);
    List<YearlyTrainingSummary> findByTrainerSummaryUserName(String trainerUsername);
}
