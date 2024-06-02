package com.example.totalDuration.service;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.YearlyTrainingSummary;

import java.util.Map;

public interface WorkHoursService {

    void updateWorkHours(TrainerSessionWorkHoursUpdateDTO updateDTO);

    Map<Integer, YearlyTrainingSummary> retrieveWorkHours(String username);
}
