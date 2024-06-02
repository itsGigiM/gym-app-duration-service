package com.example.totalDuration.controller;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.dto.WorkHoursRetrieveDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface WorkHoursController {
    @Operation(summary = "Update the work hour of the trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated total duration")})
    ResponseEntity<HttpStatus> updateWorkHours(TrainerSessionWorkHoursUpdateDTO updateDto);

    @Operation(summary = "Retrieve the work hour details of the trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved work durations")})
    public ResponseEntity<WorkHoursRetrieveDTO> retrieveWorkHours(@PathVariable String username);
}
