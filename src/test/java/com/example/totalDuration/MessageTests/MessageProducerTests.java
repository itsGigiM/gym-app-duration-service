package com.example.totalDuration.MessageTests;

import com.example.totalDuration.dto.WorkHoursRetrieveDTO;
import com.example.totalDuration.message.MessageProducer;
import com.example.totalDuration.model.YearlyTrainingSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageProducerTests {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private MessageProducer messageProducer;

    @BeforeEach
    void setUp() {
        messageProducer = new MessageProducer(jmsTemplate, "Workhours.queue", 3, "DLQ");
    }

    @Test
    void testSendWorkHoursSuccess() {
        WorkHoursRetrieveDTO updateDTO = new WorkHoursRetrieveDTO("john_doe", "John", "Doe", true, new HashMap<Integer, YearlyTrainingSummary>());
        String jsonMessage = MessageProducer.toJson(updateDTO);

        messageProducer.sendWorkHours(updateDTO);

        verify(jmsTemplate, times(1)).convertAndSend("Workhours.queue", jsonMessage);
    }

    @Test
    void testSendWorkHoursRetriesAndSendsToDLQ() {
        WorkHoursRetrieveDTO updateDTO = new WorkHoursRetrieveDTO("john_doe", "John", "Doe", true, new HashMap<Integer, YearlyTrainingSummary>());
        willAnswer( invocation -> { throw new Exception("abc msg"); }).given(jmsTemplate).convertAndSend(anyString(), anyString());
        messageProducer.sendWorkHours(updateDTO);
        verify(jmsTemplate, times(3)).convertAndSend(anyString(), anyString());
        verify(jmsTemplate, times(1)).send(eq("DLQ"), any());
    }
}

