package com.example.totalDuration;


import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.example.totalDuration.service.WorkHourServiceImpl;
import com.example.totalDuration.service.WorkHoursService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkHoursServiceTests {

    @Mock
    private TrainerRepository mockTrainerRepo;

    private WorkHoursService service;

    @BeforeEach
    public void setUp() {
        service = new WorkHourServiceImpl(mockTrainerRepo);
    }

    @Test
    public void updateWorkHours() {
        TrainerSummary trainer = new TrainerSummary();
        YearlyTrainingSummary summary = new YearlyTrainingSummary(2024, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO("username", "f", "l", true, LocalDate.of(2024, 5, 23), 10L, "ADD");

        Mockito.when(mockTrainerRepo.findByUserName("username")).thenReturn(trainer);

        service.updateWorkHours(updateDTO);

        assertEquals(10L, summary.getMayDuration());
    }

    @Test
    public void testRetrieveWorkHours() {
        String username = "testUser";
        TrainerSummary trainerSummary = new TrainerSummary();
        when(mockTrainerRepo.findByUserName(username)).thenReturn(trainerSummary);

        List<YearlyTrainingSummary> yearlyTrainingSummaries = new ArrayList<>();
        YearlyTrainingSummary summary1 = new YearlyTrainingSummary();
        summary1.setTrainingYear(2020);
        yearlyTrainingSummaries.add(summary1);

        Map<Integer, YearlyTrainingSummary> result = service.retrieveWorkHours(username);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(2020));
        assertEquals(summary1, result.get(2020));
    }

}
