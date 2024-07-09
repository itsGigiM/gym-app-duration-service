package com.example.totalDuration.config;

import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.YearlyTrainingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

@Configuration
@Slf4j
public class MongoDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(TrainerRepository repository, YearlyTrainingRepository yearlyRepository) {
        return args -> {
            repository.deleteAll();
            yearlyRepository.deleteAll();

            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<TrainerSummary>> typeReference = new TypeReference<>() {};
            InputStream inputStream = new ClassPathResource("data.json").getInputStream();
            List<TrainerSummary> trainers = mapper.readValue(inputStream, typeReference);
            for (TrainerSummary trainer : trainers) {
                List<YearlyTrainingSummary> yearlySummaries = trainer.getYearlyTrainingSummaries();
                yearlyRepository.saveAll(yearlySummaries);
            }

            repository.saveAll(trainers);
            log.info("Data initialization completed.");
        };
    }
}
