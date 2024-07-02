package com.example.totalDuration.steps;

import com.example.totalDuration.SpringComponentTests;
import com.example.totalDuration.controller.WorkHoursController;
import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.model.TrainerSummary;
import com.example.totalDuration.model.YearlyTrainingSummary;
import com.example.totalDuration.repository.TrainerRepository;
import com.example.totalDuration.repository.YearlyTrainingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
@Slf4j
@AutoConfigureMockMvc
public class WorkHoursSteps extends SpringComponentTests {

    private final WorkHoursController workHoursController;
    private final TrainerRepository trainerRepository;
    private final YearlyTrainingRepository yearlyTrainingRepository;
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MTk0OTE5NTAsImV4cCI6MTg3NzI1ODM1MCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.KrLUiOUV76mHpr76IAmb8NTj2hQ_v2qJFRLHSbshEG4";
    @Before
    public void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    @When("a trainer modifies a training with username {string}, first name {string}, last name {string}, training date {string}, duration {string}, and action type {string}")
    public void aTrainerModifiesANewTrainingWithUsernameFirstNameLastNameTrainingDateDurationAndActionType(String username, String firstName, String lastName, String trainingDate, String duration, String actionType){
        Long durationInHours = Long.parseLong(duration);
        String url = "http://localhost:8080/work-hours";
        TrainerSessionWorkHoursUpdateDTO request = new TrainerSessionWorkHoursUpdateDTO(username, firstName, lastName, true, LocalDate.parse(trainingDate), durationInHours, actionType);
        mockMvc = MockMvcBuilders.standaloneSetup(workHoursController).build();
        try {
            String json = objectMapper.writeValueAsString(request);
            mockMvc.perform(post(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e.getMessage());
        }
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
        TrainerSessionWorkHoursUpdateDTO request = new TrainerSessionWorkHoursUpdateDTO(username, firstName, lastName,
                true, LocalDate.of(2024, 1, 3), 2L, "ADD");
        String url = "http://localhost:8080/work-hours";
        mockMvc = MockMvcBuilders.standaloneSetup(workHoursController).build();
        try {
            String json = objectMapper.writeValueAsString(request);
            mockMvc.perform(post(url)
                            .header("Authorization", "Bearer " + TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e.getMessage());
        }

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
        String url = "http://localhost:8080/work-hours/" + username;
        mockMvc = MockMvcBuilders.standaloneSetup(workHoursController).build();
        try {
            mockMvc.perform(get(url)
                            .header("Authorization", "Bearer " + TOKEN))
                    .andExpect(status().isOk());
            fail();

        } catch (Exception ignored) {
        }
    }
}
