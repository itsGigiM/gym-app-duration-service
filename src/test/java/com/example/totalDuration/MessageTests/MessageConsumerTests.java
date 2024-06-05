package com.example.totalDuration.MessageTests;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.message.MessageConsumer;
import com.example.totalDuration.service.WorkHoursService;
import com.example.totalDuration.utils.TokenValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageConsumerTests {

    @Mock
    private WorkHoursService workHoursService;

    @Mock
    private TokenValidator tokenValidator;

    @InjectMocks
    private MessageConsumer messageConsumer;

    @BeforeEach
    void setUp() {
        messageConsumer = new MessageConsumer(workHoursService, tokenValidator);
    }

    @Test
    void testReceiveMessageWithValidToken(){
        // Arrange
        String jsonMessage = "{\n" +
                "  \"trainerUsername\": \"admin\",\n" +
                "  \"firstName\": \"admin\",\n" +
                "  \"lastName\": \"admin\",\n" +
                "  \"isActive\": true,\n" +
                "  \"trainingDate\": \"2024-06-03\",\n" +
                "  \"trainingDuration\": 1,\n" +
                "  \"actionType\": \"ADD\"\n" +
                "}";
        TrainerSessionWorkHoursUpdateDTO expectedUpdateDTO = new TrainerSessionWorkHoursUpdateDTO("admin", "admin", "admin",
                true, LocalDate.of(2024, 06, 03),1L, "ADD");
        when(tokenValidator.invalidToken(anyString())).thenReturn(false);

        messageConsumer.receiveMessage(jsonMessage, "valid_token");

        verify(workHoursService, times(1)).updateWorkHours(expectedUpdateDTO);
    }

    @Test
    void testReceiveMessageWithInvalidToken() {
        String jsonMessage = "{\"username\": \"admin\", \"hours\": 5}";
        when(tokenValidator.invalidToken(anyString())).thenReturn(true);

        messageConsumer.receiveMessage(jsonMessage, "invalid_token");

        verify(workHoursService, never()).updateWorkHours(any());
    }

}
