package com.example.totalDuration.repository;

import com.example.totalDuration.model.TrainerSummary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

public interface TrainerRepository extends CrudRepository<TrainerSummary, Long> {
    TrainerSummary findByUserName(String userName);
}
