package com.example.totalDuration.handler;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Username can not be found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Date you inputted is invalid");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleMalformedJwtException() {
        log.error("Invalid JWT");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
