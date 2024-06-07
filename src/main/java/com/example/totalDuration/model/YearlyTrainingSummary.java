package com.example.totalDuration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class YearlyTrainingSummary {

    @Id
    @JsonProperty("_id")
    private String id;

    private Integer trainingYear;
    private Long januaryDuration;
    private Long februaryDuration;
    private Long marchDuration;
    private Long aprilDuration;
    private Long mayDuration;
    private Long juneDuration;
    private Long julyDuration;
    private Long augustDuration;
    private Long septemberDuration;
    private Long octoberDuration;
    private Long novemberDuration;
    private Long decemberDuration;

    public YearlyTrainingSummary(Integer trainingYear, Long januaryDuration, Long februaryDuration,
                                 Long marchDuration, Long aprilDuration, Long mayDuration,
                                 Long juneDuration, Long julyDuration, Long augustDuration,
                                 Long septemberDuration, Long octoberDuration,
                                 Long novemberDuration, Long decemberDuration) {
        this.trainingYear = trainingYear;
        this.januaryDuration = januaryDuration;
        this.februaryDuration = februaryDuration;
        this.marchDuration = marchDuration;
        this.aprilDuration = aprilDuration;
        this.mayDuration = mayDuration;
        this.juneDuration = juneDuration;
        this.julyDuration = julyDuration;
        this.augustDuration = augustDuration;
        this.septemberDuration = septemberDuration;
        this.octoberDuration = octoberDuration;
        this.novemberDuration = novemberDuration;
        this.decemberDuration = decemberDuration;
    }
}