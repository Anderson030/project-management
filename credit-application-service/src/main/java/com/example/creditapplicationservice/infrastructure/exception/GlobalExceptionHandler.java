package com.example.creditapplicationservice.infrastructure.exception;

import com.example.creditapplicationservice.domain.exception.BusinessException;
import com.example.creditapplicationservice.domain.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage(), "resource-not-found");
    }

    // (400 - Bad Request)
    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException ex) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage(), "business-rule-violation");
    }

    // @Valid (400 - Bad Request with details)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problem = buildProblemDetail(HttpStatus.BAD_REQUEST, "Error de validación en los datos enviados", "validation-error");

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        problem.setProperty("errors", errors);
        return problem;
    }

    // (409 - Conflict)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityException(DataIntegrityViolationException ex) {
        String detail = "Conflicto de integridad de datos. Posible duplicado o referencia inválida.";
        return buildProblemDetail(HttpStatus.CONFLICT, detail, "data-integrity-conflict");
    }

    // (500 - Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        ex.printStackTrace();
        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en el sistema.", "internal-server-error");
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String detail, String typeSuffix) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);

        problem.setTitle(status.getReasonPhrase());
        problem.setType(URI.create("https://coopcredit.com/errors/" + typeSuffix)); // [cite: 110]
        problem.setProperty("timestamp", LocalDateTime.now()); // [cite: 118]
        problem.setProperty("traceId", UUID.randomUUID().toString()); // [cite: 119]

        return problem;
    }
}
