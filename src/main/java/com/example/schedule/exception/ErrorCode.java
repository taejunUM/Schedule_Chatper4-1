package com.example.schedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "Email or password does not match.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
