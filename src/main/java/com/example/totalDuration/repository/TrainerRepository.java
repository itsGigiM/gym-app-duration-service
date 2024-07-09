package com.example.totalDuration.repository;

import com.example.totalDuration.model.TrainerSummary;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.repository.MongoRepository;

@CompoundIndexes({
        @CompoundIndex(name = "firstName_lastName_index", def = "{'firstName': 1, 'lastName': 1}")
})
public interface TrainerRepository extends MongoRepository<TrainerSummary, String> {
    TrainerSummary findByUserName(String userName);
}
