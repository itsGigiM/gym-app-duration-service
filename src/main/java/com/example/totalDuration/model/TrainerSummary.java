package com.example.totalDuration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSummary{

    @Id
    private String userId;

    private String firstName;

    private String lastName;

    private String userName;

    @JsonProperty("isActive")
    private boolean isActive;

    @DocumentReference
    private List<YearlyTrainingSummary> yearlyTrainingSummaries;

    public TrainerSummary(String firstName, String lastName, String userName, boolean isActive,
                          List<YearlyTrainingSummary> yearlyTrainingSummaries) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isActive = isActive;
        this.yearlyTrainingSummaries = yearlyTrainingSummaries;
    }
}
