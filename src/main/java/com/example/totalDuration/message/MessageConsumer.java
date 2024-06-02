package com.example.totalDuration.message;

import com.example.totalDuration.dto.TrainerSessionWorkHoursUpdateDTO;
import com.example.totalDuration.service.WorkHoursService;
import com.example.totalDuration.utils.TokenValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final WorkHoursService service;

    private final TokenValidator tokenValidator;

    public MessageConsumer(WorkHoursService service, TokenValidator tokenValidator) {
        this.service = service;
        this.tokenValidator = tokenValidator;
        objectMapper.registerModule(new JavaTimeModule());

    }

    public TrainerSessionWorkHoursUpdateDTO fromJson(String json) {
        try {
            return objectMapper.readValue(json, TrainerSessionWorkHoursUpdateDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON into object {}", e.toString());
            return null;
        }
    }

    @JmsListener(destination = "${work.hours.queue}")
    public void receiveMessage(String message, @Header("Authorization") String token){
        if(tokenValidator.invalidToken(token)){
            log.error("Token is invalid");
            return;
        }
        log.info("Received message {}", message);
        TrainerSessionWorkHoursUpdateDTO updateDTO = fromJson(message);
        service.updateWorkHours(updateDTO);
    }
}
