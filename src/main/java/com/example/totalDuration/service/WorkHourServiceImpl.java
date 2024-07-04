package com.example.totalDuration.service;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.example.totalDuration.repository.YearlyTrainingRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@NoArgsConstructor
public class WorkHourServiceImpl implements WorkHoursService{
    TrainerRepository trainerRepository;
    YearlyTrainingRepository yearlyRepository;

    @Autowired
    public WorkHourServiceImpl(TrainerRepository trainerRepo, YearlyTrainingRepository yearlyRepository) {
        this.yearlyRepository = yearlyRepository;
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
            trainer = new TrainerSummary(updateDTO.getFirstName(), updateDTO.getLastName(), username,
                    updateDTO.getIsActive(), new ArrayList<>());
        }

        YearlyTrainingSummary summary = getYearlyTrainingSummary(trainer, yr);

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
        yearlyRepository.save(summary);
        trainerRepository.save(trainer);
        log.info("Updated trainers work hours {}", updateDTO);
    }

    private YearlyTrainingSummary getYearlyTrainingSummary(TrainerSummary trainer, int yr) {
        YearlyTrainingSummary summary = null;

        List<YearlyTrainingSummary> summaries = trainer.getYearlyTrainingSummaries();

        for(YearlyTrainingSummary tempSummary : summaries){
            if(tempSummary.getTrainingYear() == yr){
                summary = tempSummary;
                break;
            }
        }

        if(summary == null){
            summary = new YearlyTrainingSummary(
                    yr, 0L, 0L, 0L, 0L, 0L,
                    0L, 0L, 0L, 0L, 0L,
                    0L, 0L
            );
            trainer.getYearlyTrainingSummaries().add(summary);
        }
        return summary;
    }

    @Override
    public Map<Integer, YearlyTrainingSummary> retrieveWorkHours(String username) {
        TrainerSummary trainer = trainerRepository.findByUserName(username);
        if (trainer != null) {
            Map<Integer, YearlyTrainingSummary> map = new HashMap<>();
            List<YearlyTrainingSummary> list = trainer.getYearlyTrainingSummaries();
            for(YearlyTrainingSummary summary : list){
                map.put(summary.getTrainingYear(), summary);
            }
            return map;
        }
        throw new RuntimeException("Trainer with username " + username + " not found.");
    }
}