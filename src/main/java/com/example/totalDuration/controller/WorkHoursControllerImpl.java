package com.example.totalDuration.controller;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.dto.WorkHoursRetrieveDTO;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.service.WorkHoursService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/work-hours")
@Slf4j
@NoArgsConstructor
public class WorkHoursControllerImpl implements WorkHoursController{
    WorkHoursService workHoursService;

    @Autowired
    public WorkHoursControllerImpl(WorkHoursService workHoursService) {
        this.workHoursService = workHoursService;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> updateWorkHours(@RequestBody TrainerSessionWorkHoursUpdateDTO updateDto) {
        log.info("Received POST request to update trainer's work hours. Request details: {}", updateDto);
        workHoursService.updateWorkHours(updateDto);
        log.info("Work hours updated successfuly");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<WorkHoursRetrieveDTO> retrieveWorkHours(@PathVariable String username) {
        log.info("Received GET request to retrieve trainer's work hours. Request details: username - {}", username);
        Map<Integer, YearlyTrainingSummary> map = workHoursService.retrieveWorkHours(username);
        log.info("Work hours retrieved successfuly");
        return new ResponseEntity<>(
                new WorkHoursRetrieveDTO(username, username, username, true, map),
                HttpStatus.OK);
    }
}
