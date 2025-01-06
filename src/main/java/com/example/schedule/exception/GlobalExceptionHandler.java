package com.example.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ErrorResponseEntity> handleGlobalException(GlobalException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

}
