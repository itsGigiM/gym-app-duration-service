package com.example.totalDuration.dto;

import com.example.totalDuration.model.YearlyTrainingSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class WorkHoursRetrieveDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private boolean isActive;
    private Map<Integer, YearlyTrainingSummary> yearlyTrainingSummaries;
}
