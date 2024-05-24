package com.example.totalDuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.dto.WorkHoursRetrieveDTO;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.service.WorkHoursService;
import com.example.totalDuration.controller.WorkHoursControllerImpl;

@ExtendWith(MockitoExtension.class)
public class ControllerTests {

    @Mock
    private WorkHoursService service;

    @InjectMocks
    private WorkHoursControllerImpl controller;

    @BeforeEach
    public void setUp() {
        controller = new WorkHoursControllerImpl(service);
    }

    @Test
    public void testUpdateWorkHours() {
        TrainerSessionWorkHoursUpdateDTO updateDto = new TrainerSessionWorkHoursUpdateDTO();
        doNothing().when(service).updateWorkHours(updateDto);

        ResponseEntity<HttpStatus> responseEntity = controller.updateWorkHours(updateDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testRetrieveWorkHours() {
        Map<Integer, YearlyTrainingSummary> map = new HashMap<>();
        map.put(2024, new YearlyTrainingSummary());

        when(service.retrieveWorkHours("username")).thenReturn(map);

        ResponseEntity<WorkHoursRetrieveDTO> responseEntity = controller.retrieveWorkHours("username");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        WorkHoursRetrieveDTO responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("username", responseBody.getFirstName());
        assertEquals("username", responseBody.getLastName());
        assertTrue(responseBody.isActive());
        assertEquals(map, responseBody.getYearlyTrainingSummaries());
    }
}
