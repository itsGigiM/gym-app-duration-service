package com.example.totalDuration.service;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.example.totalDuration.repository.YearlySummaryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@NoArgsConstructor
public class WorkHourServiceImpl implements WorkHoursService{
    YearlySummaryRepository repository;
    TrainerRepository trainerRepository;

    @Autowired
    public WorkHourServiceImpl(YearlySummaryRepository repository, TrainerRepository trainerRepo) {
        this.repository = repository;
        this.trainerRepository = trainerRepo;
    }

    @Transactional
    public void updateWorkHours(TrainerSessionWorkHoursUpdateDTO updateDTO) {
        int yr = updateDTO.getTrainingDate().getYear();
        int mth = updateDTO.getTrainingDate().getMonthValue();
        Long duration = updateDTO.getTrainingDuration();
        if(updateDTO.getActionType().equals("DELETE")) duration *= -1;
        String username = updateDTO.getTrainerUsername();

        TrainerSummary trainer = trainerRepository.findByUserName(username);

        if (trainer == null) {
            log.info("Trainer is being processed first time in the database");
            trainer = new TrainerSummary(updateDTO.getFirstName(), updateDTO.getLastName(), username, updateDTO.getIsActive());
            trainerRepository.save(trainer);
        }

        TrainerSummary finalTrainer = trainer;
        YearlyTrainingSummary summary = repository.findByTrainerSummaryUserNameAndTrainingYear(
                updateDTO.getTrainerUsername(), yr).orElseGet(() -> {
            YearlyTrainingSummary newSummary = new YearlyTrainingSummary(
            yr, 0L, 0L, 0L, 0L, 0L,
                    0L, 0L, 0L, 0L, 0L,
                    0L, 0L, finalTrainer
            );
            repository.save(newSummary);
            return newSummary;
        });;

        switch (mth) {
            case 1:
                summary.setJanuaryDuration(summary.getJanuaryDuration() + duration);
                break;
            case 2:
                summary.setFebruaryDuration(summary.getFebruaryDuration() + duration);
                break;
            case 3:
                summary.setMarchDuration(summary.getMarchDuration() + duration);
                break;
            case 4:
                summary.setAprilDuration(summary.getAprilDuration() + duration);
                break;
            case 5:
                summary.setMayDuration(summary.getMayDuration() + duration);
                break;
            case 6:
                summary.setJuneDuration(summary.getJuneDuration() + duration);
                break;
            case 7:
                summary.setJulyDuration(summary.getJulyDuration() + duration);
                break;
            case 8:
                summary.setAugustDuration(summary.getAugustDuration() + duration);
                break;
            case 9:
                summary.setSeptemberDuration(summary.getSeptemberDuration() + duration);
                break;
            case 10:
                summary.setOctoberDuration(summary.getOctoberDuration() + duration);
                break;
            case 11:
                summary.setNovemberDuration(summary.getNovemberDuration() + duration);
                break;
            case 12:
                summary.setDecemberDuration(summary.getDecemberDuration() + duration);
                break;
            default:
                throw new IllegalArgumentException("Invalid month: " + mth);
        }
        repository.save(summary);
        log.info("Updated trainers work hours");

    }

    @Override
    public Map<Integer, YearlyTrainingSummary> retrieveWorkHours(String username) {
        TrainerSummary trainer = trainerRepository.findByUserName(username);
        if (trainer != null) {
            Map<Integer, YearlyTrainingSummary> map = new HashMap<>();
            List<YearlyTrainingSummary> list = repository.findByTrainerSummaryUserName(username);
            for(YearlyTrainingSummary summary : list){
                map.put(summary.getTrainingYear(), summary);
            }
            return map;
        }
        throw new EntityNotFoundException("Trainer with username " + username + " not found.");
    }
}
