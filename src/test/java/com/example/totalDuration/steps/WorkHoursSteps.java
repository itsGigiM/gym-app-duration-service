package com.example.totalDuration.steps;

import com.example.totalDuration.SpringComponentTests;
import com.example.totalDuration.controller.WorkHoursController;
import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.example.totalDuration.repository.YearlyTrainingRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor
@Slf4j
public class WorkHoursSteps extends SpringComponentTests {

    private final WorkHoursController workHoursController;
    private final TrainerRepository trainerRepository;
    private final YearlyTrainingRepository yearlyTrainingRepository;

    @When("a trainer modifies a training with username {string}, first name {string}, last name {string}, training date {string}, duration {string}, and action type {string}")
    public void aTrainerModifiesANewTrainingWithUsernameFirstNameLastNameTrainingDateDurationAndActionType(String username, String firstName, String lastName, String trainingDate, String duration, String actionType){
        Long durationInHours = Long.parseLong(duration);
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO(username, firstName, lastName,
                true, LocalDate.parse(trainingDate), durationInHours, actionType);
        workHoursController.updateWorkHours(updateDTO);
    }

    @Then("the training hours should be successfully added to the system with username {string}")
    public void theTrainingHoursOfTrainingDateAndDurationShouldBeSuccessfullyAddedToTheSystemWithUsername(String username) {
        TrainerSummary trainer = trainerRepository.findByUserName(username);
        assertNotNull(trainer);
    }

    @Then("the training hours of username {string} should be {int}")
    public void theTrainingHoursShouldBeSuccessfullyDeletedFromTheSystem(String username, int hours) {
        TrainerSummary trainer = trainerRepository.findByUserName(username);
        assertEquals(hours, trainer.getYearlyTrainingSummaries().getFirst().getJanuaryDuration().intValue());
    }

    @Given("a trainer with username {string}, first name {string}, last name {string} has only worked duration of 2 hours in the january of 2024 and is now stored in the system")
    public void aTrainerWithUsernameFirstNameLastNameHasOnlyWorkedDurationOfHoursInTheJanuaryOfAndIsNowStoredInTheSystem(String username, String firstName, String lastName) {
        trainerRepository.deleteAll();
        yearlyTrainingRepository.deleteAll();
        TrainerSessionWorkHoursUpdateDTO updateDTO = new TrainerSessionWorkHoursUpdateDTO(username, firstName, lastName,
                true, LocalDate.of(2024, 1, 3), 2L, "ADD");
        workHoursController.updateWorkHours(updateDTO);

    }

    @Then("the response of training summary should contain the correct training summary duration for the trainer {string}")
    public void theResponseOfTrainingSummaryShouldContainTheCorrectTrainingSummaryDurationForTheTrainer(String username) {
        Map<Integer, YearlyTrainingSummary> mp = Objects.requireNonNull(workHoursController.retrieveWorkHours(username)
                .getBody()).getYearlyTrainingSummaries();
        try {
            assertEquals(2, mp.get(2024).getJanuaryDuration());
        } catch (Exception e) {
            fail("Trainer does not exists");
        }
    }

    @Given("system database is clean and there is no trainer's summary stored")
    public void systemDatabaseIsCleanAndThereIsNoTrainerSSummaryStored() {
        yearlyTrainingRepository.deleteAll();
        trainerRepository.deleteAll();
    }

    @Then("the request of training summary of the trainer {string} should throw exception")
    public void theRequestOfTrainingSummaryOfTheTrainerShouldThrowException(String username) {
        assertThrows(RuntimeException.class, () -> workHoursController.retrieveWorkHours(username));
    }
}
