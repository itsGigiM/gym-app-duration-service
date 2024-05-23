package com.example.totalDuration;


import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.example.totalDuration.repository.YearlySummaryRepository;
import com.example.totalDuration.service.WorkHourServiceImpl;
import com.example.totalDuration.service.WorkHoursService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @Mock
    private TrainerRepository mockTrainerRepo;

    @Mock
    private YearlySummaryRepository mockSummaryRepo;

    private WorkHoursService service;

    @BeforeEach
    public void setUp() {
        service = new WorkHourServiceImpl(mockSummaryRepo, mockTrainerRepo);
    }

    @Test
    public void updateWorkHours() {
        TrainerSummary trainer = new TrainerSummary();
        YearlyTrainingSummary summary = new YearlyTrainingSummary(2024, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, trainer);
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO("username", "f", "l", true, LocalDate.of(2024, 5, 23), 10L, "ADD");

        Mockito.when(mockTrainerRepo.findByUserName("username")).thenReturn(trainer);
        Mockito.when(mockSummaryRepo.findByTrainerSummaryUserNameAndTrainingYear("username", 2024)).thenReturn(Optional.of(summary));

        service.updateWorkHours(updateDTO);

        assertEquals(10L, summary.getMayDuration());
    }

}
