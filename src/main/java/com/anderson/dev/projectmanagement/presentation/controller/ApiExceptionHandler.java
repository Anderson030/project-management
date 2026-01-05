package com.anderson.dev.projectmanagement.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbidden(RuntimeException ex) {
        if ("Forbidden".equals(ex.getMessage())) {
            return ex.getMessage();
        }
        throw ex;
    }
}
