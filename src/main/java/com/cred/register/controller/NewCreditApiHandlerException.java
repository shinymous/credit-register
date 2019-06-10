package com.cred.register.controller;

import com.cred.register.dto.ErrorDTO;
import com.cred.register.exception.InvalidDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
@RestController
@Slf4j
public class NewCreditApiHandlerException extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDTO errorDetails = this.buildErrorDetails(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<Object> handleAllExceptions(InvalidDataException ex, WebRequest request) {
        ErrorDTO errorDetails = this.buildErrorDetails(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleAllExceptions(HttpClientErrorException ex, WebRequest request) {
        ErrorDTO errorDetails = this.buildErrorDetails(ex, request, ex.getStatusCode());
        return new ResponseEntity(errorDetails, ex.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public final ResponseEntity<Object> handleAllExceptions(HttpServerErrorException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> errorMap = objectMapper.readValue(ex.getResponseBodyAsString(), Map.class);
            errorDTO.setMessage((String) errorMap.get("message"));
            errorDTO.setPath((String) errorMap.get("path"));
            errorDTO.setError((String) errorMap.get("error"));
            errorDTO.setStatus((int) errorMap.get("status"));
            errorDTO.setTimestamp((LocalDateTime) errorMap.get("timestamp"));
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResponseEntity(errorDTO, ex.getStatusCode());
    }

    private ErrorDTO buildErrorDetails(Exception ex, WebRequest request, HttpStatus httpStatus) {
        log.error(ex.getMessage(), ex);
        return new ErrorDTO(LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getContextPath());
    }
}
