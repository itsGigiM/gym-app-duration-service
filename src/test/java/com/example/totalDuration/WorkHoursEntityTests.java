package com.example.totalDuration;

import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkHoursEntityTests {

    private YearlyTrainingSummary yearlyTrainingSummary;
    private final TrainerSummary trainerSummary = new TrainerSummary();

    @BeforeEach
    void setUp() {
        yearlyTrainingSummary = new YearlyTrainingSummary(
                2023,
                10L, 20L, 30L, 40L, 50L, 60L,
                70L, 80L, 90L, 100L, 110L, 120L
        );
    }

    @Test
    void testGetters() {
        assertEquals(2023, yearlyTrainingSummary.getTrainingYear());
        assertEquals(10L, yearlyTrainingSummary.getJanuaryDuration());
        assertEquals(20L, yearlyTrainingSummary.getFebruaryDuration());
        assertEquals(30L, yearlyTrainingSummary.getMarchDuration());
        assertEquals(40L, yearlyTrainingSummary.getAprilDuration());
        assertEquals(50L, yearlyTrainingSummary.getMayDuration());
        assertEquals(60L, yearlyTrainingSummary.getJuneDuration());
        assertEquals(70L, yearlyTrainingSummary.getJulyDuration());
        assertEquals(80L, yearlyTrainingSummary.getAugustDuration());
        assertEquals(90L, yearlyTrainingSummary.getSeptemberDuration());
        assertEquals(100L, yearlyTrainingSummary.getOctoberDuration());
        assertEquals(110L, yearlyTrainingSummary.getNovemberDuration());
        assertEquals(120L, yearlyTrainingSummary.getDecemberDuration());
    }

    @Test
    void testSetters() {
        yearlyTrainingSummary.setTrainingYear(2024);
        yearlyTrainingSummary.setJanuaryDuration(15L);
        yearlyTrainingSummary.setFebruaryDuration(25L);
        yearlyTrainingSummary.setMarchDuration(35L);
        yearlyTrainingSummary.setAprilDuration(45L);
        yearlyTrainingSummary.setMayDuration(55L);
        yearlyTrainingSummary.setJuneDuration(65L);
        yearlyTrainingSummary.setJulyDuration(75L);
        yearlyTrainingSummary.setAugustDuration(85L);
        yearlyTrainingSummary.setSeptemberDuration(95L);
        yearlyTrainingSummary.setOctoberDuration(95L);
        yearlyTrainingSummary.setNovemberDuration(95L);
        yearlyTrainingSummary.setDecemberDuration(95L);
        assertEquals(85L, yearlyTrainingSummary.getAugustDuration());
    }
}
